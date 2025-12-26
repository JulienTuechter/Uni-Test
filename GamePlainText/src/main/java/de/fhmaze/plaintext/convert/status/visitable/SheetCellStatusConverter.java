package de.fhmaze.plaintext.convert.status.visitable;

import de.fhmaze.engine.status.visitable.SheetCellStatus;
import de.fhmaze.plaintext.convert.status.VisitableCellStatusConverter;

public class SheetCellStatusConverter extends VisitableCellStatusConverter<SheetCellStatus> {
    @Override
    public String serialize(SheetCellStatus sheetStatus) {
        String data = "SHEET";
        return data + super.serialize(sheetStatus);
    }

    @Override
    public SheetCellStatus parse(String data) {
        String[] parts = data.split(" ");
        return parse(parts, 1, SheetCellStatus::new);
    }
}
