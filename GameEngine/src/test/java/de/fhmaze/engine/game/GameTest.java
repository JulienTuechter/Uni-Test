package de.fhmaze.engine.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.InitAction;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.DirectionActionResult;
import de.fhmaze.engine.action.result.NotOkayActionResult;
import de.fhmaze.engine.action.result.OkayActionResult;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Form;
import de.fhmaze.engine.game.cell.Maze;
import de.fhmaze.engine.game.player.Player;
import de.fhmaze.engine.game.player.PlayerFactory;
import de.fhmaze.engine.game.player.PlayerParams;
import de.fhmaze.engine.init.MazeInfo;
import de.fhmaze.engine.init.PlayerInfo;
import de.fhmaze.engine.io.ActionSender;
import de.fhmaze.engine.io.GameIOFactory;
import de.fhmaze.engine.io.GameInputReader;
import de.fhmaze.engine.io.GameLogger;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.FloorCellStatus;
import de.fhmaze.engine.status.FormCellStatus;
import de.fhmaze.engine.turn.TurnInfo;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameTest {
    private Game game;

    @Mock
    private GameIOFactory gameIOFactory;

    @Mock
    private PlayerFactory playerFactory;

    @Mock
    private GameInputReader reader;

    @Mock
    private ActionSender sender;

    @Mock
    private GameLogger logger;

    @Mock
    private Player player;

    @BeforeEach
    void setUp() {
        game = new Game(gameIOFactory, playerFactory);
    }

    @Test
    void testStartMazeCreated() throws IOException {
        // Arrange
        configureGameIOFactory();
        MazeInfo mazeInfo = new MazeInfo(12, 8, 3);
        configureMazeCreation(mazeInfo);
        configurePlayerCreation(new PlayerInfo(2, new Position(4, 2), 0));
        configureTurns(0);

        // Act
        game.start();

        // Assert
        Maze maze = game.getMaze();
        assertEquals(mazeInfo.sizeX(), maze.getSizeX());
        assertEquals(mazeInfo.sizeY(), maze.getSizeY());
        assertEquals(mazeInfo.level(), maze.getLevel());
    }

    @Test
    void testStartPlayerCreated() throws IOException {
        // Arrange
        configureGameIOFactory();
        configureMazeCreation(new MazeInfo(12, 8, 3));
        PlayerInfo playerInfo = new PlayerInfo(2, new Position(4, 2), 0);
        configurePlayerCreation(playerInfo);
        configureTurns(0);

        // Act
        game.start();

        // Assert
        verify(playerFactory)
            .createPlayer(argThat(matchPlayerParams(playerInfo.start())));
    }

    @Test
    void testStartCellSetStatus() throws IOException {
        // Arrange
        configureGameIOFactory();
        configureMazeCreation(new MazeInfo(12, 8, 3));
        Position start = new Position(4, 2);
        configurePlayerCreation(new PlayerInfo(2, start, 0));
        configureTurns(1, new TurnInfo(new OkayActionResult(), new FormCellStatus(2, 5), new CellStatus[4]));
        configurePlayerCells(start);

        // Act
        game.start();

        // Assert
        Maze maze = game.getMaze();
        Cell cell = maze.getCell(start);
        assertTrue(cell.hasForm());
        Form form = cell.getForm();
        assertEquals(2, form.playerId());
        assertEquals(5, form.formId());
    }

    @Test
    void testStartCurrentCellVisitOnFirstTurn() throws IOException {
        // Arrange
        configureGameIOFactory();
        configureMazeCreation(new MazeInfo(12, 8, 3));
        Position start = new Position(4, 2);
        configurePlayerCreation(new PlayerInfo(2, start, 0));
        configureTurns(1, new TurnInfo(new OkayActionResult(), new FloorCellStatus(), new CellStatus[4]));
        configurePlayerCells(start);

        // Act
        game.start();

        // Assert
        Maze maze = game.getMaze();
        Cell cell = maze.getCell(start);
        assertTrue(cell.isVisited());
    }

    @Test
    void testStartCurrentCellVisitOnNextTurns() throws IOException {
        // Arrange
        configureGameIOFactory();
        configureMazeCreation(new MazeInfo(12, 8, 3));
        Position[] positions = new Position[] {
            new Position(4, 2),
            new Position(2, 7),
            new Position(5, 0)
        };
        configurePlayerCreation(new PlayerInfo(2, positions[0], 0));
        Direction direction = Direction.NORTH;
        configureTurns(
            positions.length,
            new TurnInfo[] {
                new TurnInfo(new OkayActionResult(), new FloorCellStatus(), new CellStatus[4]),
                new TurnInfo(new DirectionActionResult(direction), new FloorCellStatus(), new CellStatus[4])
            },
            turnCount -> {
                Position position = positions[turnCount - 1];
                configurePlayerCells(position);
            });
        when(player.chooseNextAction())
            .thenReturn(new GoAction(direction));

        // Act
        game.start();

        // Assert
        Maze maze = game.getMaze();
        for (Position position : positions) {
            Cell cell = maze.getCell(position);
            assertTrue(cell.isVisited());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void testStartPlayerOnTurnSuccess(int turns) throws IOException {
        // Arrange
        configureGameIOFactory();
        configureMazeCreation(new MazeInfo(12, 8, 3));
        Position start = new Position(4, 2);
        configurePlayerCreation(new PlayerInfo(2, start, 0));
        ActionResult result = new OkayActionResult();
        configureTurns(turns, new TurnInfo(result, new FloorCellStatus(), new CellStatus[4]));
        configurePlayerCells(start);
        Action action = new InitAction();
        when(player.chooseNextAction())
            .thenReturn(action);

        // Act
        game.start();

        // Assert
        verify(player, times(turns))
            .onTurnSuccess(action, result);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void testStartPlayerOnTurnFailure(int turns) throws IOException {
        // Arrange
        configureGameIOFactory();
        configureMazeCreation(new MazeInfo(12, 8, 3));
        Position start = new Position(4, 2);
        configurePlayerCreation(new PlayerInfo(2, start, 0));
        ActionResult result = new NotOkayActionResult();
        configureTurns(turns, new TurnInfo(result, new FloorCellStatus(), new CellStatus[4]));
        configurePlayerCells(start);
        Action action = new InitAction();
        when(player.chooseNextAction())
            .thenReturn(action);

        // Act
        game.start();

        // Assert
        verify(player, times(turns))
            .onTurnFailure(action, result);
    }

    @Test
    void testStartPlayerChooseNextTurn() throws IOException {
        // Arrange
        configureGameIOFactory();
        configureMazeCreation(new MazeInfo(12, 8, 3));
        Position start = new Position(4, 2);
        configurePlayerCreation(new PlayerInfo(2, start, 0));
        configureTurns(1, new TurnInfo(new OkayActionResult(), new FloorCellStatus(), new CellStatus[4]));
        configurePlayerCells(start);

        // Act
        game.start();

        // Assert
        verify(player)
            .chooseNextAction();
    }

    @Test
    void testStartSenderSendAction() throws IOException {
        // Arrange
        configureGameIOFactory();
        configureMazeCreation(new MazeInfo(12, 8, 3));
        Position start = new Position(4, 2);
        configurePlayerCreation(new PlayerInfo(2, start, 0));
        configureTurns(1, new TurnInfo(new OkayActionResult(), new FloorCellStatus(), new CellStatus[4]));
        configurePlayerCells(start);
        Action action = new GoAction(Direction.NORTH);
        when(player.chooseNextAction())
            .thenReturn(action);

        // Act
        game.start();

        // Assert
        verify(sender)
            .sendAction(action);
    }

    @Test
    void testStartReaderClose() throws IOException {
        // Arrange
        configureGameIOFactory();
        configureMazeCreation(new MazeInfo(12, 8, 3));
        configurePlayerCreation(new PlayerInfo(2, new Position(4, 2), 0));
        configureTurns(0);

        // Act
        game.start();

        // Assert
        verify(reader)
            .close();
    }

    private void configureGameIOFactory() {
        when(gameIOFactory.createGameInputReader())
            .thenReturn(reader);
        when(gameIOFactory.createActionSender())
            .thenReturn(sender);
        when(gameIOFactory.createGameLogger())
            .thenReturn(logger);
    }

    private void configureMazeCreation(MazeInfo mazeInfo) {
        when(reader.readMazeInfo())
            .thenReturn(mazeInfo);
    }

    private ArgumentMatcher<PlayerParams> matchPlayerParams(Position start) {
        return params -> params.id() == 2 && params.start() == start && params.maze() == game.getMaze();
    }

    private void configurePlayerCreation(PlayerInfo playerInfo) {
        when(reader.readPlayerInfo())
            .thenReturn(playerInfo);
        when(playerFactory.createPlayer(argThat(matchPlayerParams(playerInfo.start()))))
            .thenReturn(player);
    }

    private void configurePlayerCells(Position position) {
        when(player.getCurrentCell())
            .thenAnswer(_ -> game.getMaze().getCell(position));
        when(player.getNeighborCell(any(Direction.class)))
            .thenAnswer(i -> game.getMaze().getNeighborCell(position, i.getArgument(0)));
    }

    private void configureTurns(int turns, TurnInfo[] turnInfos, Consumer<Integer> turnCountConsumer) {
        AtomicInteger turnCount = new AtomicInteger(0);
        when(reader.hasNextTurn())
            .thenAnswer(_ -> {
                if (turnCount.get() == turns) return false;
                turnCount.incrementAndGet();
                if (turnCountConsumer != null) {
                    turnCountConsumer.accept(turnCount.get());
                }
                return true;
            });
        if (turns == 0) return;
        when(reader.readTurnInfo())
            .thenAnswer(_ -> {
                int index = Math.min(turnCount.get(), turnInfos.length) - 1;
                return turnInfos[index];
            });
    }

    private void configureTurns(int turns, TurnInfo[] turnInfos) {
        configureTurns(turns, turnInfos, null);
    }

    private void configureTurns(int turns, TurnInfo turnInfo) {
        configureTurns(turns, new TurnInfo[] { turnInfo });
    }

    private void configureTurns(int turns) {
        configureTurns(turns, new TurnInfo[0]);
    }
}
