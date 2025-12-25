package de.fhmaze.plaintext.convert.status;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.WallCellStatus;

public class WallCellStatusConverter implements Converter<CellStatus> {
    @Override
    public String serialize(CellStatus cellStatus) {
        return "WALL";
    }

    @Override
    public WallCellStatus parse(String data) {
        return new WallCellStatus();
    }
}
