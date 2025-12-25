package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotKnowledge;
import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.bot.game.SheetKnowledge;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.PutAction;
import de.fhmaze.engine.action.TakeAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.cell.Cell;

/**
 * Sheet-Strategie: Intelligentes Handling von Blättern.
 *
 * Aufnehmen (TAKE): Wenn das Blatt seine eigene Form/Finish verdeckt
 * Ablegen (PUT): Um Ziele vom Feind zu verdecken
 */
public class SheetStrategy implements BotStrategy {

    @Override
    public Action suggestAction(BotPlayer player) {
        Cell currentCell = player.getCurrentCell();
        int sheetCount = player.getSheetCount();
        int nextFormId = player.getFormCount() + 1;
        BotKnowledge knowledge = player.getKnowledge();
        SheetKnowledge sheetKnowledge = player.getSheetKnowledge();

        // 1. Sheet aufnehmen, wenn sinnvoll (eigenes Ziel verdeckt)
        if (currentCell.hasSheet() && sheetKnowledge.shouldTakeSheet(currentCell, nextFormId, sheetCount)) {
            sheetKnowledge.markSheetRemoved(currentCell);
            return new TakeAction();
        }

        // 2. Sheet ablegen, wenn auf feindlichem Ziel und wir eines haben
        if (sheetCount > 0 && !currentCell.hasSheet() && sheetKnowledge.isEnemyTarget(currentCell)) {
            return new PutAction();
        }

        // 3. Zu feindliches Ziel in Nachbarschaft entdeckt
        if (sheetCount > 0) {
            Direction direction = findAdjacentEnemyTarget(player, sheetKnowledge);
            if (direction != null) {
                return new GoAction(direction);
            }
        }

        // 4. Navigation zu bekannten feindlichen Zielen
        if (sheetCount > 0) {
            Direction direction = navigateToEnemyTarget(player, knowledge);
            if (direction != null) {
                return new GoAction(direction);
            }
        }

        return null;
    }

    private Direction findAdjacentEnemyTarget(BotPlayer player, SheetKnowledge sheetKnowledge) {
        for (Direction direction : Direction.values()) {
            Cell targetCell = player.getNeighborCell(direction);
            if (targetCell == null) continue;

            // Wir wollen zu einem Feld, das ein gegnerisches Ziel ist, aber NOCH KEIN Sheet hat
            if (!targetCell.hasSheet() && sheetKnowledge.isEnemyTarget(targetCell)) {
                if (targetCell.isVisitable() && !targetCell.hasEnemy()) {
                    return direction;
                }
            }
        }
        return null;
    }

    private Direction navigateToEnemyTarget(BotPlayer player, BotKnowledge knowledge) {
        Cell currentCell = player.getCurrentCell();
        // Suche nur nach Zielen ohne Sheet
        Cell enemyTarget = knowledge.findNearestUnsheetedEnemyTarget(currentCell);
        if (enemyTarget == null) return null;

        Direction direction = knowledge.nextDirectionTowards(currentCell, enemyTarget);
        if (direction == null) return null;

        Cell nextCell = player.getNeighborCell(direction);
        if (nextCell != null && nextCell.isVisitable() && !nextCell.hasEnemy()) {
            return direction;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Sheet";
    }
}
