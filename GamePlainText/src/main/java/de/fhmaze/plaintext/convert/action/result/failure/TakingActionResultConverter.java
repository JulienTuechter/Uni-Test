package de.fhmaze.plaintext.convert.action.result.failure;

import de.fhmaze.engine.action.result.failure.TakingActionResult;
import de.fhmaze.plaintext.convert.action.result.FailureActionResultConverter;

public class TakingActionResultConverter extends FailureActionResultConverter<TakingActionResult> {
    @Override
    public String serialize(TakingActionResult takingResult) {
        return super.serialize(takingResult) + " TAKING";
    }

    @Override
    public TakingActionResult parse(String data) {
        return new TakingActionResult();
    }
}
