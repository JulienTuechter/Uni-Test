package de.fhmaze.plaintext.convert.action.result.success;

import de.fhmaze.engine.action.result.success.OkayActionResult;
import de.fhmaze.plaintext.convert.action.result.SuccessActionResultConverter;

public class OkayActionResultConverter extends SuccessActionResultConverter<OkayActionResult> {
    @Override
    public String serialize(OkayActionResult okayResult) {
        return super.serialize(okayResult);
    }

    @Override
    public OkayActionResult parse(String data) {
        return new OkayActionResult();
    }
}
