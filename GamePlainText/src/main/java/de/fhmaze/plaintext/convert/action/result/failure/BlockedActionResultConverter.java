package de.fhmaze.plaintext.convert.action.result.failure;

import de.fhmaze.engine.action.result.failure.BlockedActionResult;
import de.fhmaze.plaintext.convert.action.result.FailureActionResultConverter;

public class BlockedActionResultConverter extends FailureActionResultConverter<BlockedActionResult> {
    @Override
    public String serialize(BlockedActionResult blockedResult) {
        return super.serialize(blockedResult) + " BLOCKED";
    }

    @Override
    public BlockedActionResult parse(String data) {
        return new BlockedActionResult();
    }
}
