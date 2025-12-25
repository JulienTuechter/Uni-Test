package de.fhmaze.plaintext.convert.status.log;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.status.CellStatus;

public class LogUnknownCellStatusSerializer implements Serializer<CellStatus> {
    @Override
    public String serialize(CellStatus cellStatus) {
        return "??";
    }
}
