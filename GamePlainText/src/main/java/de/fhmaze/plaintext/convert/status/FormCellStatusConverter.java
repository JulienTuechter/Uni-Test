package de.fhmaze.plaintext.convert.status;

import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.FormCellStatus;

public class FormCellStatusConverter extends VisitableCellStatusConverter {
    @Override
    public String serialize(CellStatus cellStatus) {
        FormCellStatus formStatus = (FormCellStatus) cellStatus;
        String data = "FORM " + formStatus.getPlayerId() + " " + formStatus.getFormId();
        return data + super.serialize(formStatus);
    }

    @Override
    public FormCellStatus parse(String data) {
        String[] parts = data.split(" ");
        int playerId = Integer.parseInt(parts[1]);
        int formId = Integer.parseInt(parts[2]);
        return parse(parts, 3, enemyDistance -> new FormCellStatus(playerId, formId, enemyDistance));
    }
}
