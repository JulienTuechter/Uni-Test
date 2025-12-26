package de.fhmaze.engine.action;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.success.OkayActionResult;

public record FinishAction() implements Action {
    @Override
    public boolean successful(ActionResult result) {
        return result instanceof OkayActionResult;
    }
}
