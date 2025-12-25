package de.fhmaze.engine.turn;

import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.turn.listener.CellStatusListener;
import de.fhmaze.engine.turn.listener.TurnListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Entwurfsmuster (Verhalten): Observer
public class TurnManager {
    private final List<TurnListener> turnListeners = new ArrayList<>();
    private final List<CellStatusListener> cellStatusListeners = new ArrayList<>();

    public void addTurnListener(TurnListener turnListener) {
        turnListeners.add(turnListener);
    }

    public void addTurnListeners(TurnListener... turnListeners) {
        Collections.addAll(this.turnListeners, turnListeners);
    }

    private void notifyTurnSuccess(Action action, ActionResult actionResult) {
        for (TurnListener turnListener : turnListeners) {
            turnListener.onTurnSuccess(action, actionResult);
        }
    }

    private void notifyTurnFailure(Action action, ActionResult actionResult) {
        for (TurnListener turnListener : turnListeners) {
            turnListener.onTurnFailure(action, actionResult);
        }
    }

    public void addCellStatusListener(CellStatusListener cellStatusListener) {
        cellStatusListeners.add(cellStatusListener);
    }

    public void addCellStatusListeners(CellStatusListener... cellStatusListeners) {
        Collections.addAll(this.cellStatusListeners, cellStatusListeners);
    }

    private void notifyCellStatusUpdate(CellStatus currentCellStatus, CellStatus[] neighborCellStatuses) {
        for (CellStatusListener cellStatusListener : cellStatusListeners) {
            cellStatusListener.onCellStatusUpdate(currentCellStatus, neighborCellStatuses);
        }
    }

    public void process(Action lastAction, TurnInfo turnInfo) {
        if (lastAction.successful(turnInfo.lastActionResult())) {
            notifyTurnSuccess(lastAction, turnInfo.lastActionResult());
        } else {
            notifyTurnFailure(lastAction, turnInfo.lastActionResult());
        }
        notifyCellStatusUpdate(turnInfo.currentCellStatus(), turnInfo.neighborCellStatuses());
    }
}
