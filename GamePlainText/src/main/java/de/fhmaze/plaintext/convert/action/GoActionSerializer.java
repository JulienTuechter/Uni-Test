package de.fhmaze.plaintext.convert.action;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.common.Direction;

public class GoActionSerializer implements Serializer<GoAction> {
    @Override
    public String serialize(GoAction goAction) {
        Direction direction = goAction.direction();
        return "go " + direction.toString().toLowerCase();
    }
}
