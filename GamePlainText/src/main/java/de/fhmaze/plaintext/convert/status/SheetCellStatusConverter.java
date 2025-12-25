package de.fhmaze.plaintext.convert.status;

import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.SheetCellStatus;

public class SheetCellStatusConverter extends VisitableCellStatusConverter {
    @Override
    public String serialize(CellStatus cellStatus) {
        String data = "SHEET";
        return data + super.serialize(cellStatus);
    }

    @Override
    public SheetCellStatus parse(String data) {
        String[] parts = data.split(" ");
        return parse(parts, 1, SheetCellStatus::new);
    }
}
