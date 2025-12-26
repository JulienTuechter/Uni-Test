package de.fhmaze.plaintext.convert.status.visitable;

import de.fhmaze.engine.status.visitable.FinishCellStatus;
import de.fhmaze.plaintext.convert.status.VisitableCellStatusConverter;

public class FinishCellStatusConverter extends VisitableCellStatusConverter<FinishCellStatus> {
    @Override
    public String serialize(FinishCellStatus finishStatus) {
        String data = "FINISH " + finishStatus.getPlayerId() + " " + finishStatus.getFormCount();
        return data + super.serialize(finishStatus);
    }

    @Override
    public FinishCellStatus parse(String data) {
        String[] parts = data.split(" ");
        int playerId = Integer.parseInt(parts[1]);
        int formCount = Integer.parseInt(parts[2]);
        return parse(parts, 3, enemyDistance -> new FinishCellStatus(playerId, formCount, enemyDistance));
    }
}
