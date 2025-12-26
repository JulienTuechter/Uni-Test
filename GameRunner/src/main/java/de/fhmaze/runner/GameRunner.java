package de.fhmaze.runner;

import de.fhmaze.bot.game.BotPlayerFactory;
import de.fhmaze.bot.game.BotPlayerFactory.BotStrategyProvider;
import de.fhmaze.bot.strategy.*;
import de.fhmaze.communication.protocol.ProtocolFactory;
import de.fhmaze.console.io.ConsoleGameIOFactory;
import de.fhmaze.engine.game.Game;
import de.fhmaze.engine.game.player.PlayerFactory;
import de.fhmaze.engine.io.GameIOFactory;
import de.fhmaze.engine.io.GameLogger;
import de.fhmaze.plaintext.protocol.PlainTextProtocolFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameRunner {
    private static final GameIOFactory GAME_IO_FACTORY = createGameIOFactory();
    private static final PlayerFactory PLAYER_FACTORY = createPlayerFactory();

    public static void main() {
        try {
            Game game = new Game(GAME_IO_FACTORY, PLAYER_FACTORY);
            game.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static GameIOFactory createGameIOFactory() {
        ProtocolFactory protocolFactory = new PlainTextProtocolFactory();
        return new ConsoleGameIOFactory(protocolFactory);
    }

    private static PlayerFactory createPlayerFactory() {
        BotStrategyProvider strategyProvider = level -> {
            List<BotStrategy> strategies = new ArrayList<>();
            strategies.add(new AvoidanceStrategy());
            if (level > 3)
                strategies.add(new KickStrategy());
            if (level > 4)
                strategies.add(new SheetStrategy());
            strategies.add(new NavigationStrategy());
            strategies.add(new ExplorationStrategy());
            strategies.add(new BacktrackStrategy());
            strategies.add(new FallbackStrategy());
            return strategies;
        };
        GameLogger logger = GAME_IO_FACTORY.createGameLogger();
        return new BotPlayerFactory(strategyProvider, logger);
    }
}
