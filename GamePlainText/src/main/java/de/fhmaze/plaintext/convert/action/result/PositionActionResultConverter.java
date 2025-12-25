package de.fhmaze.plaintext.convert.action.result;

import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.PositionActionResult;
import de.fhmaze.engine.common.Position;

public class PositionActionResultConverter extends OkayActionResultConverter {
    @Override
    public String serialize(ActionResult actionResult) {
        PositionActionResult positionResult = (PositionActionResult) actionResult;
        Position position = positionResult.getPosition();
        return super.serialize(actionResult) + " " + position.x() + " " + position.y();
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
