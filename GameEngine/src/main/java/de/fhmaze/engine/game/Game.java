package de.fhmaze.engine.game;

import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.InitAction;
import de.fhmaze.engine.game.cell.Maze;
import de.fhmaze.engine.game.player.Player;
import de.fhmaze.engine.game.player.PlayerFactory;
import de.fhmaze.engine.init.InitFactory;
import de.fhmaze.engine.init.MazeInfo;
import de.fhmaze.engine.init.PlayerInfo;
import de.fhmaze.engine.io.ActionSender;
import de.fhmaze.engine.io.GameIOFactory;
import de.fhmaze.engine.io.GameInputReader;
import de.fhmaze.engine.io.GameLogger;
import de.fhmaze.engine.turn.TurnInfo;
import de.fhmaze.engine.turn.TurnManager;
import java.io.IOException;

public class Game {
    private final GameIOFactory gameIOFactory;
    private final PlayerFactory playerFactory;

    private GameInputReader reader;
    private ActionSender sender;
    private GameLogger logger;

    private Maze maze;
    private Player player;

    public Game(GameIOFactory gameIOFactory, PlayerFactory playerFactory) {
        this.gameIOFactory = gameIOFactory;
        this.playerFactory = playerFactory;
    }

    public void start() throws IOException {
        // IO-Komponenten initialisieren
        reader = gameIOFactory.createGameInputReader();
        sender = gameIOFactory.createActionSender();
        logger = gameIOFactory.createGameLogger();

        // Spiel initialisieren
        InitFactory initFactory = new GameInitFactory(() -> maze, playerFactory);
        init(initFactory);

        // Turn-Schleife starten
        TurnManager turnManager = new GameTurnManager(player, logger);
        turnLoop(turnManager);

        // Spiel beenden
        stop();
    }

    private void init(InitFactory initFactory) {
        // Maze-Informationen einlesen und Maze erstellen
        MazeInfo mazeInfo = reader.readMazeInfo();
        maze = initFactory.createMaze(mazeInfo);

        // Spieler-Informationen einlesen und Spieler erstellen
        PlayerInfo playerInfo = reader.readPlayerInfo();
        player = initFactory.createPlayer(playerInfo);
    }

    private void turnLoop(TurnManager turnManager) {
        Action action = new InitAction();
        while (reader.hasNextTurn()) {
            // Turn-Informationen einlesen und verarbeiten
            TurnInfo turnInfo = reader.readTurnInfo();
            turnManager.process(action, turnInfo);

            // Debug-Informationen ausgeben
            logger.logLastActionResult(turnInfo.lastActionResult());
            logger.logCurrentCell(player.getCurrentCell());
            logger.logNeighborCells(player.getNeighborCells());

            // Spieler-Aktion erfragen und senden
            action = player.chooseNextAction();
            sender.sendAction(action);
        }
    }

    public void stop() throws IOException {
        reader.close();
    }

    public Maze getMaze() {
        return maze;
    }
}
