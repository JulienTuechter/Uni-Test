package de.fhmaze.plaintext.convert.status.log;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.status.WallCellStatus;

public class LogWallCellStatusSerializer implements Serializer<WallCellStatus> {
    @Override
    public String serialize(WallCellStatus wallStatus) {
        return "##";
    }
}
