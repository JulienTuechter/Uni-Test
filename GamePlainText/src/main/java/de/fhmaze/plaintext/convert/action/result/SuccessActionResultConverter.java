package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.engine.action.result.SuccessActionResult;

public abstract class SuccessActionResultConverter<T extends SuccessActionResult> implements Converter<T> {
    @Override
    public String serialize(T successResult) {
        return "OK";
    }
}
