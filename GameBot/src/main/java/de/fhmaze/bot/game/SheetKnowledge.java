package de.fhmaze.bot.game;

import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Finish;
import de.fhmaze.engine.game.cell.Form;
import java.util.HashSet;
import java.util.Set;

/**
 * Verwaltet das Wissen über Sheets im Labyrinth.
 *
 * - Tracking von eigenen gelegten Sheets,
 *      wie bei Landmienen muss man auch wissen wo man die geplantet hat
 * - Erkennung feindlicher Ziele
 * - Entscheidungslogik für Sheet-Aufnahme
 */
public class SheetKnowledge {

    private final int playerId;
    private final BotKnowledge botKnowledge;
    private final Set<Cell> sheetsPlacedByUs = new HashSet<>();

    public SheetKnowledge(int playerId, BotKnowledge botKnowledge) {
        this.playerId = playerId;
        this.botKnowledge = botKnowledge;
    }

    public void markSheetPlaced(Cell cell) {
        sheetsPlacedByUs.add(cell);
    }

    public void markSheetRemoved(Cell cell) {
        sheetsPlacedByUs.remove(cell);
    }

    public boolean isSheetPlacedByUs(Cell cell) {
        return sheetsPlacedByUs.contains(cell);
    }

    public boolean isEnemyTarget(Cell cell) {
        return hasEnemyForm(cell) || hasEnemyFinish(cell);
    }

    public boolean hasEnemyForm(Cell cell) {
        if (!cell.hasForm()) return false;
        Form form = cell.getForm();
        return form != null && form.playerId() != playerId && form.playerId() != -1;
    }

    public boolean hasEnemyFinish(Cell cell) {
        if (!cell.hasFinish()) return false;
        Finish finish = cell.getFinish();
        return finish != null && finish.playerId() != playerId && finish.playerId() != -1;
    }

    public boolean isEnemyTargetCovered(Cell cell) {
        return botKnowledge.getEnemyForms().contains(cell)
            || botKnowledge.getEnemyFinishCells().contains(cell);
    }

    /**
     * Entscheidet, ob ein Sheet auf der aktuellen Zelle aufgenommen werden sollte.
     */
    public boolean shouldTakeSheet(Cell cell, int nextFormId, int sheetCount) {
        if (!cell.hasSheet()) return false;

        if (botKnowledge.isFormCoveredBySheet(cell, nextFormId)) return true;
        if (botKnowledge.isFinishCoveredBySheet(cell)) return true;

        if (isSheetPlacedByUs(cell)) {
            if (sheetCount == 0) {
                Cell otherTarget = botKnowledge.findNearestUnsheetedEnemyTarget(cell);
                return otherTarget != null;
            }
            return false;
        }

        if (isEnemyTargetCovered(cell)) {
            return sheetCount == 0;
        }

        return true;
    }
}
