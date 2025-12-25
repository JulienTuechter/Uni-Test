package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.WrongOrderActionResult;

public class WrongOrderActionResultConverter extends NotOkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        return super.serialize(actionResult) + " WRONGORDER";
    }

    @Override
    public WrongOrderActionResult parse(String data) {
        return new WrongOrderActionResult();
    }
}
