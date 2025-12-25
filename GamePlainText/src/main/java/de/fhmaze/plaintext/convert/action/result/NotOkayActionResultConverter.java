package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.NotOkayActionResult;

public class NotOkayActionResultConverter implements Converter<ActionResult> {
    @Override
    public String serialize(ActionResult actionResult) {
        return "NOK";
    }

    @Override
    public NotOkayActionResult parse(String data) {
        return new NotOkayActionResult();
    }
}
