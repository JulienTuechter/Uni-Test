package de.fhmaze.bot.knowledge;

import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Finish;
import de.fhmaze.engine.game.cell.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TargetTracker {
    private final int playerId;
    private final Map<Integer, Cell> knownForms = new HashMap<>();
    private final List<Cell> enemyForms = new ArrayList<>();
    private final List<Cell> enemyFinishCells = new ArrayList<>();
    private Cell finishCell;

    public TargetTracker(int playerId) {
        this.playerId = playerId;
    }

    public boolean markCell(Cell cell) {
        if (cell == null) {
            return false;
        }

        if (cell.hasForm()) {
            Form form = cell.getForm();
            if (form.playerId() == playerId) {
                knownForms.putIfAbsent(form.formId(), cell);
                return true;
            }
            if (form.playerId() != -1 && !enemyForms.contains(cell)) {
                enemyForms.add(cell);
            }
        } else if (cell.hasFinish()) {
            Finish finish = cell.getFinish();
            if (finish.playerId() == playerId) {
                finishCell = cell;
                return true;
            }
            if (finish.playerId() != -1 && !enemyFinishCells.contains(cell)) {
                enemyFinishCells.add(cell);
            }
        }
        return false;
    }

    public boolean isFormCoveredBySheet(Cell cell, int formId) {
        if (cell == null || !cell.hasSheet()) return false;
        Cell knownFormCell = knownForms.get(formId);
        return knownFormCell != null && knownFormCell.equals(cell);
    }

    public boolean isFinishCoveredBySheet(Cell cell) {
        if (cell == null || !cell.hasSheet()) return false;
        return finishCell != null && finishCell.equals(cell);
    }

    public Cell getForm(int formId) {
        return knownForms.get(formId);
    }

    public Cell getFinishCell() {
        return finishCell;
    }

    public List<Cell> getEnemyForms() {
        return Collections.unmodifiableList(enemyForms);
    }

    public List<Cell> getEnemyFinishCells() {
        return Collections.unmodifiableList(enemyFinishCells);
    }

    public void updateFormPosition(int formId, Cell newCell) {
        if (newCell != null && newCell.hasForm()) {
            Form form = newCell.getForm();
            if (form.takeable(playerId, formId)) {
                knownForms.put(formId, newCell);
            }
        }
    }
}
