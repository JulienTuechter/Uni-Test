package de.fhmaze.bot.knowledge;

import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SymmetryNavigator {
    private final int sizeX;
    private final int sizeY;
    private final MazeGraph graph;
    private final Set<Position> symmetryHints = new HashSet<>();

    public SymmetryNavigator(int sizeX, int sizeY, MazeGraph graph) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.graph = graph;
    }

    public void resolveHint(Position position) {
        symmetryHints.remove(position);
    }

    public void addSymmetryHints(Position position) {
        if (position == null) {
            return;
        }
        // Alle 3 Symmetrie-Arten prüfen
        addHintIfUnknown(new Position(sizeX - 1 - position.x(), position.y()));
        addHintIfUnknown(new Position(position.x(), sizeY - 1 - position.y()));
        addHintIfUnknown(new Position(sizeX - 1 - position.x(), sizeY - 1 - position.y()));
    }

    /**
     * Gibt alle aktuellen Symmetrie-Hinweise zurück (unbesuchte Positionen).
     */
    public Set<Position> getHints() {
        return Collections.unmodifiableSet(symmetryHints);
    }

    public Direction nextDirection(Position currentPosition) {
        if (symmetryHints.isEmpty() || currentPosition == null) {
            return null;
        }

        Position best = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Position candidate : symmetryHints) {
            int distance = wrappedManhattan(currentPosition, candidate);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }

        if (best == null) {
            return null;
        }

        int dx = minimalDelta(currentPosition.x(), best.x(), sizeX);
        int dy = minimalDelta(currentPosition.y(), best.y(), sizeY);

        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx > 0) return Direction.EAST;
            if (dx < 0) return Direction.WEST;
        }

        if (dy > 0) return Direction.SOUTH;
        if (dy < 0) return Direction.NORTH;

        return null;
    }

    private void addHintIfUnknown(Position position) {
        if (position == null) {
            return;
        }
        Cell cell = graph.cellAt(position);
        if (cell != null && cell.isVisited()) {
            return;
        }
        symmetryHints.add(position);
    }

    private int wrappedManhattan(Position a, Position b) {
        int dx = Math.abs(minimalDelta(a.x(), b.x(), sizeX));
        int dy = Math.abs(minimalDelta(a.y(), b.y(), sizeY));
        return dx + dy;
    }

    private int minimalDelta(int from, int to, int size) {
        int direct = to - from;
        int wrap = direct > 0 ? direct - size : direct + size;
        return Math.abs(direct) <= Math.abs(wrap) ? direct : wrap;
    }
}
