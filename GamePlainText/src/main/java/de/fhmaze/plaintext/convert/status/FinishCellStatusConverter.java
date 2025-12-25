package de.fhmaze.plaintext.convert.status;

import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.FinishCellStatus;

public class FinishCellStatusConverter extends VisitableCellStatusConverter {
    @Override
    public String serialize(CellStatus cellStatus) {
        FinishCellStatus finishStatus = (FinishCellStatus) cellStatus;
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
