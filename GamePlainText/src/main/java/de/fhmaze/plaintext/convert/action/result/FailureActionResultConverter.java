package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.engine.action.result.FailureActionResult;

public abstract class FailureActionResultConverter<T extends FailureActionResult> implements Converter<T> {
    @Override
    public String serialize(T failureResult) {
        return "NOK";
    }
}
