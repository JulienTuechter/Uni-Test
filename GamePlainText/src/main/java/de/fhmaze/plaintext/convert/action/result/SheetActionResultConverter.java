package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.SheetActionResult;

public class SheetActionResultConverter extends OkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        return super.serialize(actionResult) + " SHEET";
    }

    @Override
    public SheetActionResult parse(String data) {
        return new SheetActionResult();
    }
}
