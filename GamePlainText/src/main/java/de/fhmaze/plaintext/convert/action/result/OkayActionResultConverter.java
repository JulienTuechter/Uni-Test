package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.OkayActionResult;

public class OkayActionResultConverter implements Converter<ActionResult> {
    @Override
    public String serialize(ActionResult actionResult) {
        return "OK";
    }

    @Override
    public OkayActionResult parse(String data) {
        return new OkayActionResult();
    }
}
