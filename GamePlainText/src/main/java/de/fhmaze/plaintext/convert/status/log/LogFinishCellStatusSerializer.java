package de.fhmaze.plaintext.convert.status.log;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.FinishCellStatus;

public class LogFinishCellStatusSerializer implements Serializer<CellStatus> {
    @Override
    public String serialize(CellStatus cellStatus) {
        FinishCellStatus finishStatus = (FinishCellStatus) cellStatus;
        return "!" + finishStatus.getPlayerId();
    }
}
