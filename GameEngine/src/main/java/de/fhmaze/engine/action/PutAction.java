package de.fhmaze.engine.action;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.OkayActionResult;

public record PutAction() implements Action {
    @Override
    public boolean successful(ActionResult result) {
        return result instanceof OkayActionResult;
    }
}
