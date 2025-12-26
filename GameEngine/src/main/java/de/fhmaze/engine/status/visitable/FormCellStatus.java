package de.fhmaze.engine.status.visitable;

import de.fhmaze.engine.status.VisitableCellStatus;

public final class FormCellStatus extends VisitableCellStatus {
    private final int playerId;
    private final int formId;

    public FormCellStatus(int playerId, int formId, int enemyDistance) {
        super(enemyDistance);
        this.playerId = playerId;
        this.formId = formId;
    }

    public FormCellStatus(int playerId, int formId) {
        super();
        this.playerId = playerId;
        this.formId = formId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getFormId() {
        return formId;
    }
}
