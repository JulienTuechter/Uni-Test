package de.fhmaze.engine.action.result;

import de.fhmaze.engine.common.Direction;

// final = niemand darf mehr erben
public final class DirectionActionResult extends OkayActionResult {
    private final Direction direction;

    public DirectionActionResult(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
