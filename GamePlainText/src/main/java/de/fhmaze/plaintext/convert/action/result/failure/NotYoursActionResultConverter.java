package de.fhmaze.plaintext.convert.action.result.failure;

import de.fhmaze.engine.action.result.failure.NotYoursActionResult;
import de.fhmaze.plaintext.convert.action.result.FailureActionResultConverter;

public class NotYoursActionResultConverter extends FailureActionResultConverter<NotYoursActionResult> {
    @Override
    public String serialize(NotYoursActionResult notYoursResult) {
        return super.serialize(notYoursResult) + " NOTYOURS";
    }

    @Override
    public NotYoursActionResult parse(String data) {
        return new NotYoursActionResult();
    }
}
