package de.fhmaze.plaintext.convert.status;

import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.FloorCellStatus;

public class FloorCellStatusConverter extends VisitableCellStatusConverter {
    @Override
    public String serialize(CellStatus cellStatus) {
        String data = "FLOOR";
        return data + super.serialize(cellStatus);
    }

    @Override
    public FloorCellStatus parse(String data) {
        String[] parts = data.split(" ");
        return parse(parts, 1, FloorCellStatus::new);
    }
}
