package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Form;

/**
 * Such-Strategie: Der Bot sucht aktiv nach einer Form, die verschoben wurde.
 */
public class SearchStrategy implements BotStrategy {
    /** Maximale Suchtiefe (Anzahl der Schritte) */
    private static final int MAX_SEARCH_DEPTH = 5;

    private final int targetFormId;
    private int searchAttempts;

    public SearchStrategy(int targetFormId) {
        this.targetFormId = targetFormId;
    }

    @Override
    public Action suggestAction(BotPlayer player) {
        int playerId = player.getId();

        // Wenn die Suche zu lange dauert dann abbrechen
        if (searchAttempts >= MAX_SEARCH_DEPTH) {
            return null;
        }

        searchAttempts++;

        // In der Nachbarschaft nach der Form suchen
        for (Direction direction : Direction.values()) {
            Cell neighborCell = player.getNeighborCell(direction);
            if (neighborCell == null) continue;

            if (isTargetForm(neighborCell, playerId) && !neighborCell.hasEnemy()) {
                return new GoAction(direction);
            }
        }

        // Über BotKnowledge navigieren (falls Form bekannt, aber verschoben)
        Cell formCell = player.getKnowledge().getForm(targetFormId);
        if (isTargetForm(formCell, playerId)) {
            Direction direction = player.getKnowledge()
                .nextDirectionTowards(player.getCurrentCell(), formCell);
            if (direction != null) {
                Cell targetCell = player.getNeighborCell(direction);
                if (targetCell != null && !targetCell.hasEnemy()) {
                    return new GoAction(direction);
                }
            }
        }

        return null;
    }

    public void reset() {
        searchAttempts = 0;
    }

    private boolean isTargetForm(Cell cell, int playerId) {
        if (cell == null || !cell.hasForm()) {
            return false;
        }
        Form form = cell.getForm();
        return form != null && form.takeable(playerId, targetFormId);
    }

    @Override
    public String toString() {
        return "Search (Form " + targetFormId + ")";
    }
}
