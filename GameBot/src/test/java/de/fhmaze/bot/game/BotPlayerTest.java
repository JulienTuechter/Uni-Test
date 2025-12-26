package de.fhmaze.bot.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import de.fhmaze.bot.strategy.AvoidanceStrategy;
import de.fhmaze.bot.strategy.BacktrackStrategy;
import de.fhmaze.bot.strategy.BotStrategy;
import de.fhmaze.bot.strategy.ExplorationStrategy;
import de.fhmaze.bot.strategy.FallbackStrategy;
import de.fhmaze.bot.strategy.KickStrategy;
import de.fhmaze.bot.strategy.NavigationStrategy;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.FinishAction;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.KickAction;
import de.fhmaze.engine.action.TakeAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Maze;
import de.fhmaze.engine.game.player.PlayerParams;
import de.fhmaze.engine.io.GameLogger;
import de.fhmaze.engine.status.WallCellStatus;
import de.fhmaze.engine.status.visitable.FinishCellStatus;
import de.fhmaze.engine.status.visitable.FloorCellStatus;
import de.fhmaze.engine.status.visitable.FormCellStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BotPlayerTest {
    private BotPlayer player;
    private List<BotStrategy> strategies;

    @Mock
    private Maze maze;

    @Mock
    private GameLogger logger;

    @BeforeEach
    void setUp() {
        PlayerParams params = new PlayerParams(1, new Position(3, 2), 2, maze);
        strategies = new ArrayList<>();
        strategies.add(new NavigationStrategy());
        strategies.add(new KickStrategy());
        strategies.add(new AvoidanceStrategy());
        strategies.add(new ExplorationStrategy());
        strategies.add(new BacktrackStrategy());
        strategies.add(new FallbackStrategy());
        player = new BotPlayer(params, strategies, logger);
    }

    @Test
    void testChooseNextActionFinishAction() {
        // Arrange
        when(maze.getCell(player.getPosition()))
            .thenReturn(new Cell(new FinishCellStatus(1, 0)));

        // Act
        Action action = player.chooseNextAction();

        // Assert
        assertInstanceOf(FinishAction.class, action);
    }

    @Test
    void testChooseNextActionTakeAction() {
        // Arrange
        when(maze.getCell(player.getPosition()))
            .thenReturn(new Cell(new FormCellStatus(1, 1)));

        // Act
        Action action = player.chooseNextAction();

        // Assert
        assertInstanceOf(TakeAction.class, action);
    }

    @Test
    void testChooseNextActionGoActionVisitable() {
        // Arrange
        Cell[] neighbours = new Cell[] {
            new Cell(new WallCellStatus()),
            new Cell(new WallCellStatus()),
            new Cell(new FloorCellStatus()),
            new Cell(new WallCellStatus())
        };
        when(maze.getCell(player.getPosition()))
            .thenReturn(new Cell(new FloorCellStatus()));
        when(maze.getNeighborCells(player.getPosition()))
            .thenReturn(neighbours);
        for (Direction direction : Direction.values()) {
            when(maze.getNeighborCell(player.getPosition(), direction))
                .thenReturn(neighbours[direction.ordinal()]);
        }

        // Act
        Action action = player.chooseNextAction();

        // Assert
        assertInstanceOf(GoAction.class, action);
        assertEquals(Direction.SOUTH, ((GoAction) action).direction());
    }

    @Test
    void testChooseNextActionGoActionNonOccupied() {
        // Arrange
        Cell[] neighbours = new Cell[] {
            new Cell(new FloorCellStatus(0)),
            new Cell(new FloorCellStatus(0)),
            new Cell(new FloorCellStatus()),
            new Cell(new FloorCellStatus(0))
        };
        when(maze.getCell(player.getPosition()))
            .thenReturn(new Cell(new FloorCellStatus()));
        when(maze.getNeighborCells(player.getPosition()))
            .thenReturn(neighbours);
        for (Direction direction : Direction.values()) {
            when(maze.getNeighborCell(player.getPosition(), direction))
                .thenReturn(neighbours[direction.ordinal()]);
        }

        // Act
        Action action = player.chooseNextAction();

        // Assert
        assertInstanceOf(GoAction.class, action);
        assertEquals(Direction.SOUTH, ((GoAction) action).direction());
    }

    @Test
    void testChooseNextActionKickActionEnemyForm() {
        // Arrange
        Cell currentCell = new Cell(new FormCellStatus(2, 1));
        when(maze.getCell(player.getPosition())).thenReturn(currentCell);
        Cell[] neighbors = new Cell[] {
            new Cell(new FloorCellStatus()),
            new Cell(new WallCellStatus()),
            new Cell(new WallCellStatus()),
            new Cell(new WallCellStatus())
        };
        when(maze.getNeighborCells(player.getPosition())).thenReturn(neighbors);
        for (Direction direction : Direction.values()) {
            when(maze.getNeighborCell(player.getPosition(), direction))
                .thenReturn(neighbors[direction.ordinal()]);
        }

        // Act
        Action action = player.chooseNextAction();

        // Assert
        assertInstanceOf(KickAction.class, action);
        KickAction kickAction = (KickAction) action;
        assertEquals(Direction.NORTH, kickAction.direction());
    }

    @Test
    void testChooseNextActionNoKickActionAvailable() {
        // Arrange
        strategies.removeIf(strategy -> strategy instanceof KickStrategy);
        Cell currentCell = new Cell(new FormCellStatus(2, 1));
        when(maze.getCell(player.getPosition())).thenReturn(currentCell);
        Cell[] neighbors = new Cell[] {
            new Cell(new FloorCellStatus()),
            new Cell(new WallCellStatus()),
            new Cell(new WallCellStatus()),
            new Cell(new WallCellStatus())
        };
        when(maze.getNeighborCells(player.getPosition())).thenReturn(neighbors);
        for (Direction direction : Direction.values()) {
            when(maze.getNeighborCell(player.getPosition(), direction))
                .thenReturn(neighbors[direction.ordinal()]);
        }

        // Act
        Action action = player.chooseNextAction();

        // Assert
        assertNotNull(action);
        assertNotEquals(KickAction.class, action.getClass());
    }

    @Test
    void testChooseNextActionNoKickActionWhenTargetOccupied() {
        // Arrange
        Cell currentCell = new Cell(new FormCellStatus(2, 1));
        when(maze.getCell(player.getPosition())).thenReturn(currentCell);
        Cell northCell = new Cell(new FormCellStatus(1, 1));
        Cell eastCell = new Cell(new FinishCellStatus(1, 0));
        Cell southCell = new Cell(new FormCellStatus(1, 2)); // Own form (blocks kick, but not kickable)
        Cell westCell = new Cell(new WallCellStatus());
        Cell[] neighbors = new Cell[] { northCell, eastCell, southCell, westCell };
        when(maze.getNeighborCells(player.getPosition())).thenReturn(neighbors);
        when(maze.getNeighborCell(player.getPosition(), Direction.NORTH)).thenReturn(northCell);
        when(maze.getNeighborCell(player.getPosition(), Direction.EAST)).thenReturn(eastCell);

        // Act
        Action action = player.chooseNextAction();

        // Assert
        assertNotEquals(KickAction.class, action.getClass());
    }
}
