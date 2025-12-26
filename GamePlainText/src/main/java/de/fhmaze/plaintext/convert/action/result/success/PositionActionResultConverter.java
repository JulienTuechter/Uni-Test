package de.fhmaze.plaintext.convert.action.result.success;

import de.fhmaze.engine.action.result.success.PositionActionResult;
import de.fhmaze.engine.common.Position;
import de.fhmaze.plaintext.convert.action.result.SuccessActionResultConverter;

public class PositionActionResultConverter extends SuccessActionResultConverter<PositionActionResult> {
    @Override
    public String serialize(PositionActionResult positionResult) {
        Position position = positionResult.position();
        return super.serialize(positionResult) + " " + position.x() + " " + position.y();
    }

    @Override
    public PositionActionResult parse(String data) {
        String[] parts = data.split(" ");
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        Position position = new Position(x, y);
        return new PositionActionResult(position);
    }
}
