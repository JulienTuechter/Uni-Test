package de.fhmaze.engine.game.cell;

import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.InitAction;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.player.Player;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.turn.listener.CellStatusListener;
import de.fhmaze.engine.turn.listener.TurnListener;

public class CellTurnHandler implements TurnListener, CellStatusListener {
    private final Player player;

    public CellTurnHandler(Player player) {
        this.player = player;
    }

    @Override
    public void onTurnSuccess(Action action, ActionResult result) {
        if (action instanceof InitAction || action instanceof GoAction) {
            Cell currentCell = player.getCurrentCell();
            if (!currentCell.isVisited()) {
                currentCell.visit();
            }
        }
    }

    @Override
    public void onCellStatusUpdate(CellStatus currentCellStatus, CellStatus[] neighborCellStatuses) {
        Cell currentCell = player.getCurrentCell();
        currentCell.setStatus(currentCellStatus);

        for (Direction direction : Direction.values()) {
            Cell neighborCell = player.getNeighborCell(direction);
            CellStatus neighborCellStatus = neighborCellStatuses[direction.ordinal()];
            neighborCell.setStatus(neighborCellStatus);
        }
    }
}
