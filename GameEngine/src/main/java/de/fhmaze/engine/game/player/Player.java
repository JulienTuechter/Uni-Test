package de.fhmaze.engine.game.player;

import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Maze;
import de.fhmaze.engine.turn.listener.TurnListener;

public abstract class Player implements TurnListener {
    private final int id;

    private final Maze maze;
    private Position position;
    private Direction direction;

    private int formCount;
    private int sheetCount;

    public Player(PlayerParams params) {
        id = params.id();
        maze = params.maze();
        position = params.start();
        direction = Direction.NORTH;
        sheetCount = params.sheetCount();
    }

    public int getId() {
        return id;
    }

    public abstract Action chooseNextAction();

    void goDirection(Direction direction) {
        position = maze.getNeighborPosition(position, direction);
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public Maze getMaze() {
        return maze;
    }

    public Cell getCurrentCell() {
        return maze.getCell(position);
    }

    public Cell[] getNeighborCells() {
        return maze.getNeighborCells(position);
    }

    public Cell getNeighborCell(Direction direction) {
        return maze.getNeighborCell(position, direction);
    }

    void takeForm() {
        formCount++;
    }

    public int getFormCount() {
        return formCount;
    }

    void takeSheet() {
        sheetCount++;
    }

    void putSheet() {
        sheetCount--;
    }

    public int getSheetCount() {
        return sheetCount;
    }
}
