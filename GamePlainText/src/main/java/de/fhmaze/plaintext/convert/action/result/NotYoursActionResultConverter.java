package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.NotYoursActionResult;

public class NotYoursActionResultConverter extends NotOkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        return super.serialize(actionResult) + " NOTYOURS";
    }

    @Override
    public NotYoursActionResult parse(String data) {
        return new NotYoursActionResult();
    }
}
