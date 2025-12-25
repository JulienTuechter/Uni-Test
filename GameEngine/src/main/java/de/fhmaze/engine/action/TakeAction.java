package de.fhmaze.engine.action;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.FormActionResult;
import de.fhmaze.engine.action.result.SheetActionResult;

public record TakeAction() implements Action {
    @Override
    public boolean successful(ActionResult result) {
        return result instanceof FormActionResult || result instanceof SheetActionResult;
    }
}
