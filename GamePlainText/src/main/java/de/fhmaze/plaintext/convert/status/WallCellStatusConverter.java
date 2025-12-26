package de.fhmaze.plaintext.convert.status;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.engine.status.WallCellStatus;

public class WallCellStatusConverter implements Converter<WallCellStatus> {
    @Override
    public String serialize(WallCellStatus wallStatus) {
        return "WALL";
    }

    @Override
    public WallCellStatus parse(String data) {
        return new WallCellStatus();
    }
}
