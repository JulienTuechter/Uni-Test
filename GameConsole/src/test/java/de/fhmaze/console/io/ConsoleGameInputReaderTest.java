package de.fhmaze.console.io;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import de.fhmaze.communication.convert.Parser;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.DirectionActionResult;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.init.MazeInfo;
import de.fhmaze.engine.init.PlayerInfo;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.FloorCellStatus;
import de.fhmaze.engine.turn.TurnInfo;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsoleGameInputReaderTest {
    private ConsoleGameInputReader reader;
    private PipedOutputStream out;

    @Mock
    private Parser<ActionResult> actionResultParser;

    @Mock
    private Parser<CellStatus> cellStatusParser;

    @BeforeEach
    void setUp() throws IOException {
        PipedInputStream in = new PipedInputStream();
        out = new PipedOutputStream(in);
        reader = new ConsoleGameInputReader(in, actionResultParser, cellStatusParser);
    }

    @Test
    void testReadMazeInfo() throws IOException {
        // Arrange
        String input = "10 20 3\n";
        out.write(input.getBytes());
        out.flush();

        // Act
        MazeInfo mazeInfo = reader.readMazeInfo();

        // Assert
        assertEquals(10, mazeInfo.sizeX());
        assertEquals(20, mazeInfo.sizeY());
        assertEquals(3, mazeInfo.level());
    }

    @Test
    void testReadPlayerInfo() throws IOException {
        // Arrange
        String data = "5 15 25 0 \n";
        out.write(data.getBytes());
        out.flush();

        // Act
        PlayerInfo playerInfo = reader.readPlayerInfo();

        // Assert
        assertEquals(5, playerInfo.id());
        assertEquals(new Position(15, 25), playerInfo.start());
    }

    @Test
    void testHasNextTurn() throws IOException {
        // Arrange
        String data = "data\n";
        out.write(data.getBytes());
        out.flush();

        // Act
        boolean hasNextTurn = reader.hasNextTurn();

        // Assert
        assertTrue(hasNextTurn);
    }

    @Test
    void testReadTurnInfo() throws IOException {
        // Arrange
        TurnInfo expected = new TurnInfo(
            new DirectionActionResult(Direction.NORTH),
            new FloorCellStatus(),
            new CellStatus[] {
                new FloorCellStatus(),
                new FloorCellStatus(),
                new FloorCellStatus(),
                new FloorCellStatus()
            });
        String data = "AR\nCCS\nNCS\nECS\nSCS\nWCS\n";
        out.write(data.getBytes());
        out.flush();
        when(actionResultParser.parse("AR"))
            .thenReturn(expected.lastActionResult());
        when(cellStatusParser.parse("CCS"))
            .thenReturn(expected.currentCellStatus());
        when(cellStatusParser.parse("NCS"))
            .thenReturn(expected.neighborCellStatus(Direction.NORTH));
        when(cellStatusParser.parse("ECS"))
            .thenReturn(expected.neighborCellStatus(Direction.EAST));
        when(cellStatusParser.parse("SCS"))
            .thenReturn(expected.neighborCellStatus(Direction.SOUTH));
        when(cellStatusParser.parse("WCS"))
            .thenReturn(expected.neighborCellStatus(Direction.WEST));

        // Act
        TurnInfo actual = reader.readTurnInfo();

        // Assert
        assertEquals(expected.lastActionResult(), actual.lastActionResult());
        assertEquals(expected.currentCellStatus(), actual.currentCellStatus());
        assertArrayEquals(expected.neighborCellStatuses(), actual.neighborCellStatuses());
    }

    @Test
    void testClose() throws IOException {
        // Arrange
        String data = "data\n";
        out.write(data.getBytes());
        out.flush();

        // Act
        reader.close();

        // Assert
        assertThrows(IOException.class, () -> out.write(data.getBytes()));
    }
}
