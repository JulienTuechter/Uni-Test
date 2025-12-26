package de.fhmaze.plaintext.convert.status;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.engine.status.VisitableCellStatus;

public abstract class VisitableCellStatusConverter<T extends VisitableCellStatus> implements Converter<T> {
    @Override
    public String serialize(T visitableStatus) {
        if (visitableStatus.hasNoEnemyInfo())
            return "";
        else if (visitableStatus.hasEnemy())
            return " !";
        else
            return " !" + visitableStatus.getEnemyDistance();
    }

    protected T parse(String[] parts, int enemyIndex, VisitableCellStatusProvider<T> provider) {
        if (parts.length > enemyIndex) {
            if (parts[enemyIndex].length() > 1) {
                int enemyDistance = Integer.parseInt(parts[enemyIndex].substring(1));
                return provider.create(enemyDistance);
            } else {
                return provider.create(0);
            }
        }
        return provider.create(-1);
    }

    @FunctionalInterface
    public interface VisitableCellStatusProvider<T extends VisitableCellStatus> {
        T create(int enemyDistance);
    }
}
