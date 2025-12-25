package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.cell.Cell;
import java.util.Stack;

/**
 * Backtrack-Strategie: Der Bot geht seinen bekannten Pfad zurück,
 * wenn alles andere nicht mehr geht :(
 */
public class BacktrackStrategy implements BotStrategy {
    @Override
    public Action suggestAction(BotPlayer player) {
        Stack<Cell> path = player.getPath();
        Stack<Direction> turns = player.getTurns();

        if (path.size() >= 2) {
            path.pop();
            Direction previous = turns.pop();
            Direction back = previous.back();
            Cell backCell = player.getNeighborCell(back);

            if (backCell != null && !backCell.hasEnemy()) {
                return new GoAction(back);
            } else {
                path.push(player.getCurrentCell());
                turns.push(previous);
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Backtrack";
    }
}
