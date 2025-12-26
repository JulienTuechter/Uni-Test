package de.fhmaze.plaintext.convert.action.result.success;

import de.fhmaze.engine.action.result.success.DirectionActionResult;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.plaintext.convert.action.result.SuccessActionResultConverter;

public class DirectionActionResultConverter extends SuccessActionResultConverter<DirectionActionResult> {
    @Override
    public String serialize(DirectionActionResult directionResult) {
        Direction direction = directionResult.direction();
        return super.serialize(directionResult) + " " + direction;
    }

    @Override
    public DirectionActionResult parse(String data) {
        String[] parts = data.split(" ");
        Direction direction = Direction.valueOf(parts[1]);
        return new DirectionActionResult(direction);
    }
}
