package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.EmptyActionResult;

public class EmptyActionResultConverter extends NotOkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        return super.serialize(actionResult) + " EMPTY";
    }

    @Override
    public EmptyActionResult parse(String data) {
        return new EmptyActionResult();
    }
}
