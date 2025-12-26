package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotKnowledge;
import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.cell.Cell;

/**
 * Explorations-Strategie: Der Bot erkundet die Umgebung und versucht,
 * neue Zellen zu entdecken (Forms, Finish, freie Wege).
 */
public class ExplorationStrategy implements BotStrategy {
    @Override
    public Action suggestAction(BotPlayer player) {
        Cell currentCell = player.getCurrentCell();
        BotKnowledge knowledge = player.getKnowledge();

        // Direkte unbesuchte Nachbarn bevorzugen
        for (Direction direction : Direction.values()) {
            Cell neighborCell = player.getNeighborCell(direction);
            if (
                neighborCell != null && neighborCell.isVisitable()
                    && !neighborCell.isVisited() && !neighborCell.hasEnemy()
            ) {
                return new GoAction(direction);
            }
        }

        // Über bekanntes Wissen zur nächsten unbesuchten Zelle navigieren
        Direction explorationDirection = knowledge.nextDirectionToUnvisited(currentCell);
        if (explorationDirection != null) {
            Cell targetCell = player.getNeighborCell(explorationDirection);
            if (targetCell != null && !targetCell.hasEnemy()) {
                return new GoAction(explorationDirection);
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Exploration";
    }
}
