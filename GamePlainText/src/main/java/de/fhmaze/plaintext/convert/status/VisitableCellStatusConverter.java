package de.fhmaze.plaintext.convert.status;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.VisitableCellStatus;

public abstract class VisitableCellStatusConverter implements Converter<CellStatus> {
    @Override
    public String serialize(CellStatus cellStatus) {
        VisitableCellStatus visitableStatus = (VisitableCellStatus) cellStatus;
        if (visitableStatus.hasNoEnemyInfo())
            return "";
        else if (visitableStatus.hasEnemy())
            return " !";
        else
            return " !" + visitableStatus.getEnemyDistance();
    }

    <T extends VisitableCellStatus> T parse(
        String[] parts, int enemyIndex, VisitableCellStatusProvider<T> visitableCellStatusProvider
    ) {
        if (parts.length > enemyIndex) {
            if (parts[enemyIndex].length() > 1) {
                int enemyDistance = Integer.parseInt(parts[enemyIndex].substring(1));
                return visitableCellStatusProvider.create(enemyDistance);
            } else {
                return visitableCellStatusProvider.create(0);
            }
        }
        return visitableCellStatusProvider.create(-1);
    }

    @FunctionalInterface
    public interface VisitableCellStatusProvider<T extends VisitableCellStatus> {
        T create(int enemyDistance);
    }
}
