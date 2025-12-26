package de.fhmaze.plaintext.convert.action.result.failure;

import de.fhmaze.engine.action.result.failure.WrongOrderActionResult;
import de.fhmaze.plaintext.convert.action.result.FailureActionResultConverter;

public class WrongOrderActionResultConverter extends FailureActionResultConverter<WrongOrderActionResult> {
    @Override
    public String serialize(WrongOrderActionResult wrongOrderResult) {
        return super.serialize(wrongOrderResult) + " WRONGORDER";
    }

    @Override
    public WrongOrderActionResult parse(String data) {
        return new WrongOrderActionResult();
    }
}
