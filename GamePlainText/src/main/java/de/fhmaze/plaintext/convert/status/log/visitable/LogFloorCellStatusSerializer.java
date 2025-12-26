package de.fhmaze.plaintext.convert.status.log.visitable;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.status.visitable.FloorCellStatus;

public class LogFloorCellStatusSerializer implements Serializer<FloorCellStatus> {
    @Override
    public String serialize(FloorCellStatus floorStatus) {
        return "  ";
    }
}
