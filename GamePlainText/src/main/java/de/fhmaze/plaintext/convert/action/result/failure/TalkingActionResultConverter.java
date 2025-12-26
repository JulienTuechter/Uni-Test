package de.fhmaze.plaintext.convert.action.result.failure;

import de.fhmaze.engine.action.result.failure.TalkingActionResult;
import de.fhmaze.plaintext.convert.action.result.FailureActionResultConverter;

public class TalkingActionResultConverter extends FailureActionResultConverter<TalkingActionResult> {
    @Override
    public String serialize(TalkingActionResult talkingResult) {
        return super.serialize(talkingResult) + " TALKING";
    }

    @Override
    public TalkingActionResult parse(String data) {
        return new TalkingActionResult();
    }
}
