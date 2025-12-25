package de.fhmaze.console.io;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Maze;
import de.fhmaze.engine.io.GameLogger;
import de.fhmaze.engine.status.CellStatus;
import java.io.PrintStream;

// Entwurfsmuster (Erzeugung): Singleton
public class ConsoleGameLogger implements GameLogger {
    private static ConsoleGameLogger instance;

    private final PrintStream log;
    private final Serializer<ActionResult> actionResultSerializer;
    private final Serializer<CellStatus> cellStatusSerializer;
    private final Serializer<CellStatus> logCellStatusSerializer;

    private ConsoleGameLogger(
        PrintStream log,
        Serializer<ActionResult> actionResultSerializer,
        Serializer<CellStatus> cellStatusSerializer,
        Serializer<CellStatus> logCellStatusSerializer
    ) {
        this.log = log;
        this.actionResultSerializer = actionResultSerializer;
        this.cellStatusSerializer = cellStatusSerializer;
        this.logCellStatusSerializer = logCellStatusSerializer;
    }

    public static ConsoleGameLogger getInstance(
        PrintStream log,
        Serializer<ActionResult> actionResultSerializer,
        Serializer<CellStatus> cellStatusSerializer,
        Serializer<CellStatus> logCellStatusSerializer
    ) {
        if (instance == null) {
            instance = new ConsoleGameLogger(log,
                actionResultSerializer, cellStatusSerializer, logCellStatusSerializer);
        }
        return instance;
    }

    static void resetInstance() {
        instance = null;
    }

    @Override
    public void logMessage(String message, Object... args) {
        String formatted = String.format(message, args);
        log.println(formatted);
    }

    @Override
    public void logLastActionResult(ActionResult lastActionResult) {
        logActionResult("Last action result: ", lastActionResult);
    }

    private void logActionResult(String prefix, ActionResult actionResult) {
        String data = actionResultSerializer.serialize(actionResult);
        log.println(prefix + data);
    }

    @Override
    public void logCurrentCell(Cell currentCell) {
        logCell("Current cell: ", currentCell);
    }

    @Override
    public void logNeighborCells(Cell[] neighborCells) {
        for (Direction direction : Direction.values()) {
            Cell neighborCell = neighborCells[direction.ordinal()];
            logCell("Neighbor cell (" + direction + "): ", neighborCell);
        }
    }

    private void logCell(String prefix, Cell cell) {
        String data = cellStatusSerializer.serialize(cell.getStatus());
        log.println(prefix + data);
    }

    @Override
    public void logMaze(Maze maze, Cell currentCell) {
        for (int y = 0; y < maze.getSizeY(); y++) {
            for (int x = 0; x < maze.getSizeX(); x++) {
                Cell cell = maze.getCell(new Position(x, y));
                logMazeCell(cell, currentCell);
            }
            log.println();
        }
    }

    private void logMazeCell(Cell cell, Cell currentCell) {
        if (cell == currentCell)
            log.print("[]");
        else
            log.print(logCellStatusSerializer.serialize(cell.getStatus()));
    }
}
