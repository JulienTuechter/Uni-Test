package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.DirectionActionResult;
import de.fhmaze.engine.common.Direction;

public class DirectionActionResultConverter extends OkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        DirectionActionResult directionResult = (DirectionActionResult) actionResult;
        return super.serialize(actionResult) + " " + directionResult.getDirection();
    }

    @Override
    public DirectionActionResult parse(String data) {
        String[] parts = data.split(" ");
        Direction direction = Direction.valueOf(parts[1]);
        return new DirectionActionResult(direction);
    }
}
