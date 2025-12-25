package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Avoidance-Strategy: Wenn der Bot auf einen Gegner trifft, versucht er seitlich auszuweichen.
 * Erkennt auch Ping-Pong Muster, wenn zwei Bots immer wieder die Plätze tauschen.
 */
public class AvoidanceStrategy implements BotStrategy {
    private static final int HISTORY_SIZE = 6;
    private static final int FORCE_THRESHOLD = 3;
    private static final int MAX_FORCE_STEPS = 5;
    private static final int ESCAPE_STEPS = 2;

    private final Map<Position, Integer> blockedCounts = new HashMap<>();
    private final Deque<Position> positionHistory = new ArrayDeque<>(HISTORY_SIZE);

    private boolean forcingMode;
    private int forcingSteps;
    private Direction escapeDirection;
    private int escapeSteps;

    @Override
    public Action suggestAction(BotPlayer player) {
        Direction currentDirection = player.getDirection();
        Position currentPosition = player.getPosition();

        Cell leftCell = player.getNeighborCell(currentDirection.left());
        Cell rightCell = player.getNeighborCell(currentDirection.right());

        updatePositionHistory(currentPosition);

        // 1. Ping-Pong erkennen und fliehen
        Action escapeAction = handlePingPongEscape(currentDirection, leftCell, rightCell);
        if (escapeAction != null) return escapeAction;

        // 2. vor dem anderen Bot fliehen
        Action continueEscape = continueEscapeIfActive(dir -> player.getNeighborCell(dir));
        if (continueEscape != null) return continueEscape;

        // 3. Gegner erkennen und reagieren
        Direction enemyDirection = findEnemyDirection(dir -> player.getNeighborCell(dir));
        return handleEnemyEncounter(currentPosition, currentDirection, enemyDirection, leftCell, rightCell);
    }

    private void updatePositionHistory(Position position) {
        if (positionHistory.size() >= HISTORY_SIZE) {
            positionHistory.removeFirst();
        }
        positionHistory.addLast(position);
    }

    private Action handlePingPongEscape(Direction currentDirection, Cell leftCell, Cell rightCell) {
        if (!isPingPongPattern()) return null;
        return tryEscape(currentDirection, leftCell, rightCell);
    }

    private Action continueEscapeIfActive(Function<Direction, Cell> op) {
        if (escapeDirection == null || escapeSteps <= 0) return null;

        Cell escapeCell = op.apply(escapeDirection);
        if (escapeCell != null && escapeCell.isVisitable() && !escapeCell.hasEnemy()) {
            Direction directionToGo = escapeDirection;
            if (--escapeSteps == 0) {
                escapeDirection = null;
            }
            return new GoAction(directionToGo);
        }

        // Fluchtweg blockiert
        resetEscape();
        return null;
    }

    private Direction findEnemyDirection(Function<Direction, Cell> op) {
        for (Direction dir : Direction.values()) {
            Cell cell = op.apply(dir);
            if (cell != null && cell.hasEnemy()) {
                return dir;
            }
        }
        return null;
    }

    private Action handleEnemyEncounter(
        Position currentPosition,
        Direction currentDirection, Direction enemyDirection,
        Cell leftCell, Cell rightCell
    ) {
        // Force-Mode aktiv
        if (forcingMode) {
            return handleForcingMode(currentPosition, enemyDirection);
        }

        // Kein Gegner
        if (enemyDirection == null) {
            blockedCounts.remove(currentPosition);
            return null;
        }

        // Gegner gefunden, Counter erhöhen
        int count = blockedCounts.merge(currentPosition, 1, Integer::sum);

        // Nach mehreren Versuchen wird force mode aktiviert
        if (count >= FORCE_THRESHOLD) {
            forcingMode = true;
            forcingSteps = 0;
            return new GoAction(enemyDirection);
        }

        // Gegner vor uns seitlich ausweichen
        if (enemyDirection == currentDirection) {
            return trySideStep(currentDirection, leftCell, rightCell);
        }

        return null;
    }

    private Action handleForcingMode(Position currentPosition, Direction enemyDirection) {
        if (enemyDirection == null || forcingSteps >= MAX_FORCE_STEPS) {
            resetForcingMode(currentPosition);
            return null;
        }
        forcingSteps++;
        return new GoAction(enemyDirection);
    }

    private void resetForcingMode(Position currentPosition) {
        forcingMode = false;
        forcingSteps = 0;
        blockedCounts.remove(currentPosition);
    }

    private void resetEscape() {
        escapeDirection = null;
        escapeSteps = 0;
    }

    private Action trySideStep(Direction currentDirection, Cell leftCell, Cell rightCell) {
        Direction left = currentDirection.left();
        Direction right = currentDirection.right();
        Direction viable = findViableDirection(leftCell, left, rightCell, right);
        return viable != null ? new GoAction(viable) : null;
    }

    private Direction findViableDirection(Cell leftCell, Direction left, Cell rightCell, Direction right) {
        boolean leftOk = isViableUnvisited(leftCell);
        boolean rightOk = isViableUnvisited(rightCell);

        if (leftOk) return left;
        if (rightOk) return right;
        return null;
    }

    private boolean isViableUnvisited(Cell cell) {
        return cell != null && cell.isVisitable() && !cell.hasEnemy() && !cell.isVisited();
    }

    /**
     * Prüft ob ein Ping-Pong-Muster vorliegt: A -> B -> A -> B, heißt die Bots laufen die ganze zeit nur vorn und nach hinten
     */
    private boolean isPingPongPattern() {
        if (positionHistory.size() < 4) return false;

        Position[] recent = positionHistory.toArray(new Position[0]);
        int size = recent.length;

        return recent[size - 1].equals(recent[size - 3])
            && recent[size - 2].equals(recent[size - 4]);
    }

    /**
     * Versucht seitlich auszuweichen um das Ping-Pong-Muster zu durchbrechen.
     */
    private Action tryEscape(Direction currentDirection, Cell leftCell, Cell rightCell) {
        Direction left = currentDirection.left();
        Direction right = currentDirection.right();

        Direction chosen = selectEscapeDirection(leftCell, left, rightCell, right);

        if (chosen != null) {
            escapeDirection = chosen;
            escapeSteps = ESCAPE_STEPS;
            positionHistory.clear();
            return new GoAction(chosen);
        }
        return null;
    }

    private Direction selectEscapeDirection(Cell leftCell, Direction left, Cell rightCell, Direction right) {
        boolean leftViable = isViable(leftCell);
        boolean rightViable = isViable(rightCell);
        boolean leftUnvisited = leftViable && !leftCell.isVisited();
        boolean rightUnvisited = rightViable && !rightCell.isVisited();

        // Unbesuchte bevorzugen
        if (leftUnvisited) return left;
        if (rightUnvisited) return right;

        // Sonst irgendeine gangbare
        if (leftViable) return left;
        if (rightViable) return right;

        return null;
    }

    private boolean isViable(Cell cell) {
        return cell != null && cell.isVisitable() && !cell.hasEnemy();
    }

    @Override
    public String toString() {
        return "Avoidance";
    }
}
