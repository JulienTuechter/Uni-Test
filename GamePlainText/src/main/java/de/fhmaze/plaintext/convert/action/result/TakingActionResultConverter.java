package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.TakingActionResult;

public class TakingActionResultConverter extends NotOkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        return super.serialize(actionResult) + " TAKING";
    }

    @Override
    public TakingActionResult parse(String data) {
        return new TakingActionResult();
    }
}
