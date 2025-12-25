package de.fhmaze.bot.knowledge;

import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class MazeGraph {
    private final Map<Cell, Map<Direction, Cell>> adjacency = new HashMap<>();
    private final Map<Cell, Position> cellToPosition = new HashMap<>();
    private final Map<Position, Cell> positionToCell = new HashMap<>();

    public void ensureNode(Cell cell, Position position) {
        if (cell == null) {
            return;
        }
        adjacency.computeIfAbsent(cell, key -> new EnumMap<>(Direction.class));
        if (position != null) {
            cellToPosition.putIfAbsent(cell, position);
            positionToCell.putIfAbsent(position, cell);
        }
    }

    public void connect(Cell from, Direction direction, Cell to) {
        if (from == null || to == null || direction == null) {
            return;
        }
        Map<Direction, Cell> fromNeighbors = adjacency.computeIfAbsent(from, key -> new EnumMap<>(Direction.class));
        Map<Direction, Cell> toNeighbors = adjacency.computeIfAbsent(to, key -> new EnumMap<>(Direction.class));

        fromNeighbors.put(direction, to);
        toNeighbors.put(direction.back(), from);
    }

    public Map<Direction, Cell> neighbors(Cell cell) {
        return adjacency.getOrDefault(cell, Collections.emptyMap());
    }

    public Cell cellAt(Position position) {
        return positionToCell.get(position);
    }

    public Position positionOf(Cell cell) {
        return cellToPosition.get(cell);
    }

    public boolean isVisited(Position position) {
        Cell cell = cellAt(position);
        return cell != null && cell.isVisited();
    }

    public Map<Cell, Map<Direction, Cell>> adjacency() {
        return adjacency;
    }
}
