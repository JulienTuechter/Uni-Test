package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotKnowledge;
import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import java.util.Set;

/**
 * Explorations-Strategie: Der Bot erkundet die Umgebung und versucht,
 * neue Zellen zu entdecken (Forms, Finish, freie Wege).
 * Symmetrie-Hinweise werden als Bonus gewertet.
 */
public class ExplorationStrategy implements BotStrategy {
    @Override
    public Action suggestAction(BotPlayer player) {
        Cell currentCell = player.getCurrentCell();
        BotKnowledge knowledge = player.getKnowledge();
        Position currentPos = player.getPosition();

        Set<Position> symmetryHints = knowledge.getSymmetryHints();

        Direction bestDirection = null;
        double bestScore = -1;

        for (Direction direction : Direction.values()) {
            Cell neighborCell = player.getNeighborCell(direction);

            // Basis-Checks: Existiert, begehbar, kein Gegner
            if (neighborCell == null || !neighborCell.isVisitable() || neighborCell.hasEnemy()) {
                continue;
            }

            // Score berechnen
            double score = 0;

            // 1. Bonus für unbesuchte direkte Nachbarn
            if (!neighborCell.isVisited()) {
                score += 5.0;
            }

            // 2. Bonus für unbekannte Gebiete in der Tiefe (Lookahead 2 Schritte)
            int unvisitedInDepth = knowledge.countUnvisitedWithinSteps(neighborCell, 2);
            score += unvisitedInDepth * 1.0;

            // 3. Symmetrie-Einfluss: Bringt uns dieser Schritt näher an einen Symmetrie-Punkt?
            // Wir prüfen, ob dieser Schritt die Distanz zu IRGENDEINEM Symmetrie-Hinweis verringert
            Position neighborPos = player.getMaze().getNeighborPosition(currentPos, direction);
            if (neighborPos != null && !symmetryHints.isEmpty()) {
                double minDistanceCurrent = getMinDistanceToHints(currentPos, symmetryHints);
                double minDistanceNeighbor = getMinDistanceToHints(neighborPos, symmetryHints);

                if (minDistanceNeighbor < minDistanceCurrent) {
                    // Starker Bonus, wenn wir uns einem vermuteten Symmetrie-Punkt nähern
                    score += 3.0;
                }
            }

            // Wenn der Score besser ist als bisher, merken
            if (score > bestScore) {
                bestScore = score;
                bestDirection = direction;
            }
        }

        // Wenn wir eine gute Richtung gefunden haben (Score > 0 heißt es gibt einen Grund zu gehen)
        if (bestDirection != null && bestScore > 0) {
            return new GoAction(bestDirection);
        }

        // Fallback: Wenn lokal alles erkundet scheint, nutze Pathfinding zur nächsten unbesuchten Zelle
        // (Das hilft aus Sackgassen heraus, wenn Backtracking noch nicht greifen soll)
        Direction explorationDirection = knowledge.nextDirectionToUnvisited(currentCell);
        if (explorationDirection != null) {
            Cell targetCell = player.getNeighborCell(explorationDirection);
            if (targetCell != null && !targetCell.hasEnemy()) {
                return new GoAction(explorationDirection);
            }
        }

        return null;
    }

    private double getMinDistanceToHints(Position pos, Set<Position> hints) {
        double min = Double.MAX_VALUE;
        for (Position hint : hints) {
            // Manhattan Distanz reicht hier als Heuristik
            double dist = Math.abs(pos.x() - hint.x()) + Math.abs(pos.y() - hint.y());
            if (dist < min) {
                min = dist;
            }
        }
        return min;
    }

    @Override
    public String toString() {
        return "Exploration";
    }
}
