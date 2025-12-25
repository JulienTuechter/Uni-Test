package de.fhmaze.engine.status;

public abstract class VisitableCellStatus implements CellStatus {
    private final int enemyDistance;

    protected VisitableCellStatus(int enemyDistance) {
        this.enemyDistance = enemyDistance;
    }

    protected VisitableCellStatus() {
        this(-1);
    }

    public boolean hasEnemy() {
        return enemyDistance == 0;
    }

    public boolean hasEnemyInSight() {
        return enemyDistance > 0;
    }

    public boolean hasNoEnemyInfo() {
        return enemyDistance == -1;
    }

    public int getEnemyDistance() {
        return enemyDistance;
    }
}
