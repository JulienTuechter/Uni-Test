package de.fhmaze.engine.io;

import de.fhmaze.engine.init.MazeInfo;
import de.fhmaze.engine.init.PlayerInfo;
import de.fhmaze.engine.turn.TurnInfo;
import java.io.Closeable;

public interface GameInputReader extends Closeable {
    // INIT
    MazeInfo readMazeInfo();
    PlayerInfo readPlayerInfo();

    // TURN
    boolean hasNextTurn();
    TurnInfo readTurnInfo();
}
