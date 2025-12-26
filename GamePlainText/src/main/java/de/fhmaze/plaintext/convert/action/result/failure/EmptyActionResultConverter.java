package de.fhmaze.plaintext.convert.action.result.failure;

import de.fhmaze.engine.action.result.failure.EmptyActionResult;
import de.fhmaze.plaintext.convert.action.result.FailureActionResultConverter;

public class EmptyActionResultConverter extends FailureActionResultConverter<EmptyActionResult> {
    @Override
    public String serialize(EmptyActionResult emptyResult) {
        return super.serialize(emptyResult) + " EMPTY";
    }

    @Override
    public EmptyActionResult parse(String data) {
        return new EmptyActionResult();
    }
}
