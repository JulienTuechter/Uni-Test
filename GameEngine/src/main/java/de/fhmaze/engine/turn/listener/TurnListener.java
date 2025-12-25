package de.fhmaze.engine.turn.listener;

import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.result.ActionResult;

public interface TurnListener {
    default void onTurnSuccess(Action action, ActionResult result) {}
    default void onTurnFailure(Action action, ActionResult result) {}
}
