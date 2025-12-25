package de.fhmaze.engine.action;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.DirectionActionResult;
import de.fhmaze.engine.common.Direction;

public record KickAction(Direction direction) implements Action {
    @Override
    public boolean successful(ActionResult result) {
        return result instanceof DirectionActionResult;
    }
}
