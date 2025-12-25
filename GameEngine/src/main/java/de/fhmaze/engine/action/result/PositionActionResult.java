package de.fhmaze.engine.action.result;

import de.fhmaze.engine.common.Position;

public final class PositionActionResult extends OkayActionResult {
    private final Position position;

    public PositionActionResult(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
