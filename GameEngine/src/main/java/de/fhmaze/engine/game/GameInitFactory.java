package de.fhmaze.engine.game;

import de.fhmaze.engine.game.cell.Maze;
import de.fhmaze.engine.game.player.Player;
import de.fhmaze.engine.game.player.PlayerFactory;
import de.fhmaze.engine.game.player.PlayerParams;
import de.fhmaze.engine.init.InitFactory;
import de.fhmaze.engine.init.MazeInfo;
import de.fhmaze.engine.init.PlayerInfo;
import java.util.function.Supplier;

public class GameInitFactory implements InitFactory {
    private final Supplier<Maze> mazeSupplier;
    private final PlayerFactory playerFactory;

    protected GameInitFactory(Supplier<Maze> mazeSupplier, PlayerFactory playerFactory) {
        this.mazeSupplier = mazeSupplier;
        this.playerFactory = playerFactory;
    }

    @Override
    public Maze createMaze(MazeInfo mazeInfo) {
        return new Maze(mazeInfo.sizeX(), mazeInfo.sizeY(), mazeInfo.level());
    }

    @Override
    public Player createPlayer(PlayerInfo playerInfo) {
        Maze maze = mazeSupplier.get();
        PlayerParams params = new PlayerParams(playerInfo.id(), playerInfo.start(), playerInfo.sheetCount(), maze);
        return playerFactory.createPlayer(params);
    }
}
