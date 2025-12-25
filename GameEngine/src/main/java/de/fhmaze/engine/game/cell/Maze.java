package de.fhmaze.engine.game.cell;

import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.status.UnknownCellStatus;

public class Maze {
    private final Cell[][] cells;
    private final int level;

    public Maze(int sizeX, int sizeY, int level) {
        cells = new Cell[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                cells[x][y] = new Cell(new UnknownCellStatus());
            }
        }
        this.level = level;
    }

    public Cell[] getNeighborCells(Position position) {
        return new Cell[] {
            getNeighborCell(position, Direction.NORTH),
            getNeighborCell(position, Direction.EAST),
            getNeighborCell(position, Direction.SOUTH),
            getNeighborCell(position, Direction.WEST)
        };
    }

    public Cell getNeighborCell(Position position, Direction direction) {
        Position neighbor = getNeighborPosition(position, direction);
        return getCell(neighbor);
    }

    public Cell getCell(Position position) {
        return cells[position.x()][position.y()];
    }

    public Position getNeighborPosition(Position position, Direction direction) {
        int neighborX = getNeighborX(position.x(), direction);
        int neighborY = getNeighborY(position.y(), direction);
        return new Position(neighborX, neighborY);
    }

    private int getNeighborX(int x, Direction direction) {
        return ((x + direction.getDeltaX()) + getSizeX()) % getSizeX();
    }

    private int getNeighborY(int y, Direction direction) {
        return ((y + direction.getDeltaY()) + getSizeY()) % getSizeY();
    }

    public int getSizeX() {
        return cells.length;
    }

    public int getSizeY() {
        return cells[0].length;
    }

    public int getLevel() {
        return level;
    }
}
