package de.fhmaze.plaintext.convert.action.result.success;

import de.fhmaze.engine.action.result.success.SheetActionResult;
import de.fhmaze.plaintext.convert.action.result.SuccessActionResultConverter;

public class SheetActionResultConverter extends SuccessActionResultConverter<SheetActionResult> {
    @Override
    public String serialize(SheetActionResult sheetResult) {
        return super.serialize(sheetResult) + " SHEET";
    }

    @Override
    public SheetActionResult parse(String data) {
        return new SheetActionResult();
    }
}
