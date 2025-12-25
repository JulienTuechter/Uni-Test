package de.fhmaze.runner;

import de.fhmaze.bot.game.BotPlayerFactory;
import de.fhmaze.bot.game.BotPlayerFactory.BotStrategyProvider;
import de.fhmaze.bot.strategy.AvoidanceStrategy;
import de.fhmaze.bot.strategy.BacktrackStrategy;
import de.fhmaze.bot.strategy.BotStrategy;
import de.fhmaze.bot.strategy.ExplorationStrategy;
import de.fhmaze.bot.strategy.FallbackStrategy;
import de.fhmaze.bot.strategy.KickStrategy;
import de.fhmaze.bot.strategy.NavigationStrategy;
import de.fhmaze.bot.strategy.SearchStrategy;
import de.fhmaze.bot.strategy.SheetStrategy;
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

    public static void main(String[] args) { // args Parameter korrigiert für Standard main
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

            // 1. Sicherheit & Navigation
            strategies.add(new NavigationStrategy());
            strategies.add(new AvoidanceStrategy());

            // 2. Spezial-Aktionen
            if (level >= 5) {
                strategies.add(new SheetStrategy());
            }

            if (level >= 4) {
                strategies.add(new KickStrategy());
            }

            // 3. Eigene Ziele finden (WICHTIG!)
            strategies.add(new SearchStrategy(1));

            // 4. Erkundung & Fallback
            strategies.add(new ExplorationStrategy());
            strategies.add(new BacktrackStrategy());
            strategies.add(new FallbackStrategy());
            return strategies;
        };
        GameLogger logger = GAME_IO_FACTORY.createGameLogger();
        return new BotPlayerFactory(strategyProvider, logger);
    }
}
