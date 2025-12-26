package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotKnowledge;
import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.FinishAction;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.TakeAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Finish;
import de.fhmaze.engine.game.cell.Form;

/**
 * Navigations-Strategie: Der Bot hat ein bekanntes Ziel (Form oder Finish)
 * und navigiert gezielt dorthin.
 */
public class NavigationStrategy implements BotStrategy {
    @Override
    public Action suggestAction(BotPlayer player) {
        Cell currentCell = player.getCurrentCell();
        BotKnowledge knowledge = player.getKnowledge();

        int playerId = player.getId();
        int formCount = player.getFormCount();

        // Prüfen, ob wir auf einem Ziel stehen
        if (isCellFinishable(currentCell, playerId, formCount)) {
            return new FinishAction();
        }

        if (isCellTakeable(currentCell, playerId, formCount)) {
            return new TakeAction();
        }

        // Prüfen ob Ziel in direkten Nachbarn ist
        for (
            Direction direction : Direction.values()
        ) {
            Cell neighborCell = player.getNeighborCell(direction);
            if (neighborCell == null) continue;

            if (isCellFinishable(neighborCell, playerId, formCount) && !neighborCell.hasEnemy()) {
                return new GoAction(direction);
            }
        }

        //Prüfen ob Form in direkten Nachbarn ist
        for (Direction direction : Direction.values()) {
            Cell neighborCell = player.getNeighborCell(direction);
            if (neighborCell == null) continue;

            if (isCellTakeable(neighborCell, playerId, formCount) && !neighborCell.hasEnemy()) {
                return new GoAction(direction);
            }
        }

        // Navigation zum bekannten Ziel
        Cell finishCell = knowledge.getFinishCell();
        if (isCellFinishable(finishCell, playerId, formCount)) {
            Direction direction = knowledge.nextDirectionTowards(currentCell, finishCell);
            if (direction != null && !player.getNeighborCell(direction).hasEnemy()) {
                return new GoAction(direction);
            }
        }

        // Navigation zur nächsten benötigten Form
        int nextFormId = formCount + 1;
        Cell formCell = knowledge.getForm(nextFormId);

        if (isCellTakeable(formCell, playerId, formCount)) {
            Direction direction = knowledge.nextDirectionTowards(currentCell, formCell);
            if (direction != null && !player.getNeighborCell(direction).hasEnemy()) {
                return new GoAction(direction);
            }
        } else if (formCell != null && player.getMaze().getLevel() == 4) {
            // suche nach gekickter Form in der Nachbarschaft, Form wurde gekickt
            Cell displacedCell = knowledge.searchDisplacedForm(nextFormId, formCell);
            if (displacedCell != null && isCellTakeable(displacedCell, playerId, formCount)) {
                Direction direction = knowledge.nextDirectionTowards(currentCell, displacedCell);
                if (direction != null && !player.getNeighborCell(direction).hasEnemy()) {
                    return new GoAction(direction);
                }
            }
        }

        return null;
    }

    private boolean isCellFinishable(Cell cell, int playerId, int formCount) {
        if (cell == null) return false;
        Finish finish = cell.getFinish();
        if (finish != null) {
            boolean isOwnOrGeneric = finish.playerId() == -1 || finish.playerId() == playerId;
            boolean hasEnoughForms = formCount >= finish.formCount();
            return isOwnOrGeneric && hasEnoughForms;
        }
        return false;
    }

    private boolean isCellTakeable(Cell cell, int playerId, int formCount) {
        if (cell == null) return false;
        Form form = cell.getForm();
        if (form != null) {
            return form.playerId() == playerId && form.formId() == formCount + 1;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Navigation";
    }
}
