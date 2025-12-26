package de.fhmaze.plaintext.convert.action.result.failure;

import de.fhmaze.engine.action.result.failure.NotSupportedActionResult;
import de.fhmaze.plaintext.convert.action.result.FailureActionResultConverter;

public class NotSupportedActionResultConverter extends FailureActionResultConverter<NotSupportedActionResult> {
    @Override
    public String serialize(NotSupportedActionResult notSupportedResult) {
        return super.serialize(notSupportedResult) + " NOTSUPPORTED";
    }

    @Override
    public NotSupportedActionResult parse(String data) {
        return new NotSupportedActionResult();
    }
}
