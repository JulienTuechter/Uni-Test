package de.fhmaze.engine.status.visitable;

import de.fhmaze.engine.status.VisitableCellStatus;

public final class FloorCellStatus extends VisitableCellStatus {
    public FloorCellStatus(int enemyDistance) {
        super(enemyDistance);
    }

    public FloorCellStatus() {
        super();
    }
}
