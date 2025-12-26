package de.fhmaze.plaintext.convert.status.visitable;

import de.fhmaze.engine.status.visitable.FormCellStatus;
import de.fhmaze.plaintext.convert.status.VisitableCellStatusConverter;

public class FormCellStatusConverter extends VisitableCellStatusConverter<FormCellStatus> {
    @Override
    public String serialize(FormCellStatus formStatus) {
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
