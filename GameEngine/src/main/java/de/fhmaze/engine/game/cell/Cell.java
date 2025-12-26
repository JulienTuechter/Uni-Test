package de.fhmaze.engine.game.cell;

import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.VisitableCellStatus;
import de.fhmaze.engine.status.visitable.FinishCellStatus;
import de.fhmaze.engine.status.visitable.FormCellStatus;
import de.fhmaze.engine.status.visitable.SheetCellStatus;

public class Cell {
    private CellStatus status;
    private boolean visited;

    public Cell(CellStatus status) {
        this.status = status;
    }

    protected void setStatus(CellStatus status) {
        this.status = status;
    }

    public CellStatus getStatus() {
        return status;
    }

    protected void visit() {
        this.visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isVisitable() {
        return status instanceof VisitableCellStatus;
    }

    public boolean hasEnemy() {
        if (!(status instanceof VisitableCellStatus visitableStatus)) return false;
        return visitableStatus.hasEnemy();
    }

    public boolean hasEnemyInSight() {
        if (!(status instanceof VisitableCellStatus visitableStatus)) return false;
        return visitableStatus.hasEnemyInSight();
    }

    public boolean hasNoEnemyInfo() {
        if (!(status instanceof VisitableCellStatus visitableStatus)) return true;
        return visitableStatus.hasNoEnemyInfo();
    }

    public int getEnemyDistance() {
        if (!(status instanceof VisitableCellStatus visitableStatus)) return -1;
        return visitableStatus.getEnemyDistance();
    }

    public boolean hasForm() {
        return status instanceof FormCellStatus;
    }

    public Form getForm() {
        if (!(status instanceof FormCellStatus formStatus)) return null;
        return new Form(formStatus.getPlayerId(), formStatus.getFormId());
    }

    public boolean hasSheet() {
        return status instanceof SheetCellStatus;
    }

    public Sheet getSheet() {
        if (!(status instanceof SheetCellStatus)) return null;
        return new Sheet();
    }

    public boolean hasFinish() {
        return status instanceof FinishCellStatus;
    }

    public Finish getFinish() {
        if (!(status instanceof FinishCellStatus finishStatus)) return null;
        return new Finish(finishStatus.getPlayerId(), finishStatus.getFormCount());
    }
}
