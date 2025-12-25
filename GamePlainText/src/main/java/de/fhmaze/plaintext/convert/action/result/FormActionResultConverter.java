package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.FormActionResult;

public class FormActionResultConverter extends OkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        return super.serialize(actionResult) + " FORM";
    }

    @Override
    public FormActionResult parse(String data) {
        return new FormActionResult();
    }
}
