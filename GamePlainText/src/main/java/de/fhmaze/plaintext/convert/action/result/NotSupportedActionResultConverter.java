package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.NotSupportedActionResult;

public class NotSupportedActionResultConverter extends NotOkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        return super.serialize(actionResult) + " NOTSUPPORTED";
    }

    @Override
    public NotSupportedActionResult parse(String data) {
        return new NotSupportedActionResult();
    }
}
