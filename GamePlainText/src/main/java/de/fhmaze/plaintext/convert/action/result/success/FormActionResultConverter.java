package de.fhmaze.plaintext.convert.action.result.success;

import de.fhmaze.engine.action.result.success.FormActionResult;
import de.fhmaze.plaintext.convert.action.result.SuccessActionResultConverter;

public class FormActionResultConverter extends SuccessActionResultConverter<FormActionResult> {
    @Override
    public String serialize(FormActionResult formResult) {
        return super.serialize(formResult) + " FORM";
    }

    @Override
    public FormActionResult parse(String data) {
        return new FormActionResult();
    }
}
