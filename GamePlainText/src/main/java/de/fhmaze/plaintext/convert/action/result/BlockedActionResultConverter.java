package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.BlockedActionResult;

public class BlockedActionResultConverter extends NotOkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        return super.serialize(actionResult) + " BLOCKED";
    }

    @Override
    public BlockedActionResult parse(String data) {
        return new BlockedActionResult();
    }
}
