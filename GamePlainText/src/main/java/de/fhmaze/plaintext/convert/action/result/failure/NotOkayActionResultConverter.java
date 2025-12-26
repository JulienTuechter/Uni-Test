package de.fhmaze.plaintext.convert.action.result.failure;

import de.fhmaze.engine.action.result.failure.NotOkayActionResult;
import de.fhmaze.plaintext.convert.action.result.FailureActionResultConverter;

public class NotOkayActionResultConverter extends FailureActionResultConverter<NotOkayActionResult> {
    @Override
    public String serialize(NotOkayActionResult notOkayResult) {
        return super.serialize(notOkayResult);
    }

    @Override
    public NotOkayActionResult parse(String data) {
        return new NotOkayActionResult();
    }
}
