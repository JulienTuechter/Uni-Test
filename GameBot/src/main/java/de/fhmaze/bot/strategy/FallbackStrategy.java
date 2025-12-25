package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.cell.Cell;

/**
 * Fallback-Strategie: Wenn keine andere Strategie funktioniert,
 * wird eine beliebige begehbare Richtung gewählt.
 * Diese Strategie sollte immer eine Action zurückgeben.
 */
public class FallbackStrategy implements BotStrategy {
    @Override
    public Action suggestAction(BotPlayer player) {
        // Zuerst, sichere begehbare Zellen ohne Gegner suchen
        for (Direction direction : Direction.values()) {
            Cell neighborCell = player.getNeighborCell(direction);
            if (neighborCell != null && neighborCell.isVisitable() && !neighborCell.hasEnemy()) {
                return new GoAction(direction);
            }
        }

        // Wenn alle Nachbarn Gegner haben Zelle mit größter Distanz wählen
        Direction bestDirection = null;
        int maxDistance = -1;

        for (Direction direction : Direction.values()) {
            Cell neighborCell = player.getNeighborCell(direction);
            if (neighborCell != null && neighborCell.isVisitable()) {
                if (bestDirection == null) {
                    bestDirection = direction;
                }

                int dist = neighborCell.getEnemyDistance();
                if (dist > maxDistance) {
                    maxDistance = dist;
                    bestDirection = direction;
                }
            }
        }

        if (bestDirection != null) {
            return new GoAction(bestDirection);
        }

        // Fallback: aktuelle Richtung
        return new GoAction(player.getDirection());
    }

    @Override
    public String toString() {
        return "Fallback";
    }
}
