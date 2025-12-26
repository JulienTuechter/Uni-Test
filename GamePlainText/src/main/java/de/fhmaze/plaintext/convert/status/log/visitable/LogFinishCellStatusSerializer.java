package de.fhmaze.plaintext.convert.status.log.visitable;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.status.visitable.FinishCellStatus;

public class LogFinishCellStatusSerializer implements Serializer<FinishCellStatus> {
    @Override
    public String serialize(FinishCellStatus finishStatus) {
        return "!" + finishStatus.getPlayerId();
    }
}
