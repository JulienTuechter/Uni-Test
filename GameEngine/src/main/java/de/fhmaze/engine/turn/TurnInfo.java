package de.fhmaze.engine.turn;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.status.CellStatus;

public record TurnInfo(ActionResult lastActionResult, CellStatus currentCellStatus, CellStatus[] neighborCellStatuses) {
    public CellStatus neighborCellStatus(Direction direction) {
        return neighborCellStatuses[direction.ordinal()];
    }
}
