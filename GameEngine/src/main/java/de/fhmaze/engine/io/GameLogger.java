package de.fhmaze.engine.io;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Maze;

public interface GameLogger {
    void logMessage(String message, Object... args);
    void logLastActionResult(ActionResult lastActionResult);
    void logCurrentCell(Cell currentCell);
    void logNeighborCells(Cell[] neighborCells);
    void logMaze(Maze maze, Cell currentCell);
}
