package de.fhmaze.engine.action;

import de.fhmaze.engine.action.result.ActionResult;

public interface Action {
    boolean successful(ActionResult result);
}
