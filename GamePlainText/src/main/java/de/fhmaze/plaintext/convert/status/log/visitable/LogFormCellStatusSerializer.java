package de.fhmaze.plaintext.convert.status.log.visitable;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.status.visitable.FormCellStatus;

public class LogFormCellStatusSerializer implements Serializer<FormCellStatus> {
    @Override
    public String serialize(FormCellStatus formStatus) {
        return "" + (char) (formStatus.getFormId() + 64) + formStatus.getPlayerId();
    }
}
