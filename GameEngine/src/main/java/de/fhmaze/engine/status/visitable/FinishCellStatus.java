package de.fhmaze.engine.status.visitable;

import de.fhmaze.engine.status.VisitableCellStatus;

public final class FinishCellStatus extends VisitableCellStatus {
    private final int playerId;
    private final int formCount;

    public FinishCellStatus(int playerId, int formCount, int enemyDistance) {
        super(enemyDistance);
        this.playerId = playerId;
        this.formCount = formCount;
    }

    public FinishCellStatus(int playerId, int formCount) {
        super();
        this.playerId = playerId;
        this.formCount = formCount;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getFormCount() {
        return formCount;
    }
}
