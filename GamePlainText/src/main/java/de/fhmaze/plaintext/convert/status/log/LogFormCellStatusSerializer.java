package de.fhmaze.plaintext.convert.status.log;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.FormCellStatus;

public class LogFormCellStatusSerializer implements Serializer<CellStatus> {
    @Override
    public String serialize(CellStatus cellStatus) {
        FormCellStatus formStatus = (FormCellStatus) cellStatus;
        return "" + (char) (formStatus.getFormId() + 64) + formStatus.getPlayerId();
    }
}
