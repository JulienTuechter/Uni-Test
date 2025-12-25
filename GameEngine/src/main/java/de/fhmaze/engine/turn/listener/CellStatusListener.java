package de.fhmaze.engine.turn.listener;

import de.fhmaze.engine.status.CellStatus;

public interface CellStatusListener {
    void onCellStatusUpdate(CellStatus currentCellStatus, CellStatus[] neighborCellStatuses);
}
