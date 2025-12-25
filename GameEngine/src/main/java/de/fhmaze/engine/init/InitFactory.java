package de.fhmaze.engine.init;

import de.fhmaze.engine.game.cell.Maze;
import de.fhmaze.engine.game.player.Player;

// Entwurfsmuster (Erzeugung): Abstract Factory
public interface InitFactory {
    Maze createMaze(MazeInfo mazeInfo);
    Player createPlayer(PlayerInfo playerInfo);
}
