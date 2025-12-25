package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.KickAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Form;

/**
 * Kick-Strategie: Kickt fremde Formen weg, wenn sie im Weg sind
 */
public class KickStrategy implements BotStrategy {
    @Override
    public Action suggestAction(BotPlayer player) {
        Cell currentCell = player.getCurrentCell();

        if (hasEnemyForm(currentCell, player.getId())) {
            for (Direction direction : Direction.values()) {
                Cell targetCell = player.getNeighborCell(direction);
                if (isValidKickDestination(targetCell)) {
                    return new KickAction(direction);
                }
            }
        }

        return null;
    }

    private boolean isValidKickDestination(Cell targetCell) {
        if (targetCell == null) {
            return false;
        }

        if (!targetCell.isVisitable()) {
            return false;
        }

        if (targetCell.hasForm()) {
            return false;
        }

        if (targetCell.hasFinish()) {
            return false;
        }

        return true;
    }

    /**
     * Prüft, ob auf der Zelle eine fremde Form liegt.
     */
    private boolean hasEnemyForm(Cell cell, int playerId) {
        if (cell == null || !cell.isVisitable()) {
            return false;
        }

        if (cell.hasForm()) {
            Form form = cell.getForm();
            return form != null && form.playerId() != playerId;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Kick";
    }
}
