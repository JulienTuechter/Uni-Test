package de.fhmaze.console.io;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.DirectionActionResult;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Maze;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.FloorCellStatus;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsoleGameLoggerTest {
    private ConsoleGameLogger logger;
    private ByteArrayOutputStream buffer;

    @Mock
    private Serializer<ActionResult> actionResultSerializer;

    @Mock
    private Serializer<CellStatus> cellStatusSerializer;

    @Mock
    private Serializer<CellStatus> logCellStatusSerializer;

    @BeforeEach
    void setUp() {
        buffer = new ByteArrayOutputStream();
        logger = ConsoleGameLogger.getInstance(new PrintStream(buffer),
            actionResultSerializer, cellStatusSerializer, logCellStatusSerializer);
    }

    @AfterEach
    void tearDown() {
        ConsoleGameLogger.resetInstance();
    }

    @Test
    void testLogMessage() {
        // Arrange
        String expected = "18 + 7 = 187";

        // Act
        logger.logMessage("18 + 7 = %d", 187);
        String actual = buffer.toString().trim();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testLogLastActionResult() {
        // Arrange
        ActionResult actionResult = new DirectionActionResult(Direction.NORTH);
        when(actionResultSerializer.serialize(actionResult))
            .thenReturn("SerializedActionResult");
        String expected = "Last action result: SerializedActionResult";

        // Act
        logger.logLastActionResult(actionResult);
        String actual = buffer.toString().trim();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testLogCurrentCell() {
        // Arrange
        Cell cell = new Cell(new FloorCellStatus());
        when(cellStatusSerializer.serialize(cell.getStatus()))
            .thenReturn("SerializedCellStatus");
        String expected = "Current cell: SerializedCellStatus";

        // Act
        logger.logCurrentCell(cell);
        String actual = buffer.toString().trim();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testLogNeighborCells() {
        // Arrange
        Cell[] neighborCells = new Cell[4];
        for (int i = 0; i < 4; i++) {
            neighborCells[i] = new Cell(new FloorCellStatus());
            when(cellStatusSerializer.serialize(neighborCells[i].getStatus()))
                .thenReturn("SerializedCellStatus");
        }
        String[] expected = new String[] {
            "Neighbor cell (NORTH): SerializedCellStatus",
            "Neighbor cell (EAST): SerializedCellStatus",
            "Neighbor cell (SOUTH): SerializedCellStatus",
            "Neighbor cell (WEST): SerializedCellStatus"
        };

        // Act
        logger.logNeighborCells(neighborCells);
        String[] actual = buffer.toString().trim().split(System.lineSeparator());

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    void testLogMaze() {
        // Arrange
        Maze maze = new Maze(2, 2, 1);
        Cell currentCell = maze.getCell(new Position(1, 0));
        for (int x = 0; x < maze.getSizeX(); x++) {
            for (int y = 0; y < maze.getSizeY(); y++) {
                Cell cell = maze.getCell(new Position(x, y));
                if (cell == currentCell) continue;
                when(logCellStatusSerializer.serialize(cell.getStatus()))
                    .thenReturn("O");
            }
        }
        String[] expected = new String[] {
            "O[]",
            "OO"
        };

        // Act
        logger.logMaze(maze, currentCell);
        String[] actual = buffer.toString().trim().split(System.lineSeparator());

        // Assert
        assertArrayEquals(expected, actual);
    }
}
