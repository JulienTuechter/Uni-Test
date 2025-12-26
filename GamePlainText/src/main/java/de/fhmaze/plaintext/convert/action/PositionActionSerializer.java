package de.fhmaze.plaintext.convert.action;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.PositionAction;

public class PositionActionSerializer implements Serializer<PositionAction> {
    @Override
    public String serialize(PositionAction positionAction) {
        return "position";
    }
}
