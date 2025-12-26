package de.fhmaze.plaintext.convert.status.visitable;

import de.fhmaze.engine.status.visitable.FloorCellStatus;
import de.fhmaze.plaintext.convert.status.VisitableCellStatusConverter;

public class FloorCellStatusConverter extends VisitableCellStatusConverter<FloorCellStatus> {
    @Override
    public String serialize(FloorCellStatus floorStatus) {
        String data = "FLOOR";
        return data + super.serialize(floorStatus);
    }

    @Override
    public FloorCellStatus parse(String data) {
        String[] parts = data.split(" ");
        return parse(parts, 1, FloorCellStatus::new);
    }
}
