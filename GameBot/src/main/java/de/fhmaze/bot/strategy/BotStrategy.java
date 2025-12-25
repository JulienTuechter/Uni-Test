package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;

/**
 * Jede Strategie kann eine Action vorschlagen oder null zurückgeben,
 * wenn sie keine passende Action hat.
 */
public interface BotStrategy {
    /**
     * Versucht eine action basierend auf der Strategie zu bestimmen
     * @param player Der Bot, für den die Action vorgeschlagen wird
     * @return Eine Action oder null, wenn die Strategie keine Action vorschlagen kann
     */
    Action suggestAction(BotPlayer player);
}
