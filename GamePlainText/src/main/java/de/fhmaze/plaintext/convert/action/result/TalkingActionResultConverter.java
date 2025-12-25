package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.TalkingActionResult;

public class TalkingActionResultConverter extends NotOkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        return super.serialize(actionResult) + " TALKING";
    }

    @Override
    public TalkingActionResult parse(String data) {
        return new TalkingActionResult();
    }
}
