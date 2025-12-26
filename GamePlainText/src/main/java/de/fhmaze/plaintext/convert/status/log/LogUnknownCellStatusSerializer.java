package de.fhmaze.plaintext.convert.status.log;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.status.UnknownCellStatus;

public class LogUnknownCellStatusSerializer implements Serializer<UnknownCellStatus> {
    @Override
    public String serialize(UnknownCellStatus unknownStatus) {
        return "??";
    }
}
