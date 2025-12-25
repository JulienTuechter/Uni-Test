package de.fhmaze.bot.game;

import de.fhmaze.bot.strategy.BotStrategy;
import de.fhmaze.engine.game.player.PlayerFactory;
import de.fhmaze.engine.game.player.PlayerParams;
import de.fhmaze.engine.io.GameLogger;
import java.util.List;

public class BotPlayerFactory implements PlayerFactory {
    private final BotStrategyProvider strategiesProvider;
    private final GameLogger logger;

    public BotPlayerFactory(BotStrategyProvider strategyProvider, GameLogger logger) {
        this.strategiesProvider = strategyProvider;
        this.logger = logger;
    }

    @Override
    public BotPlayer createPlayer(PlayerParams params) {
        int level = params.maze().getLevel();
        List<BotStrategy> strategies = strategiesProvider.getStrategies(level);
        return new BotPlayer(params, strategies, logger);
    }

    @FunctionalInterface
    public interface BotStrategyProvider {
        List<BotStrategy> getStrategies(int level);
    }
}
