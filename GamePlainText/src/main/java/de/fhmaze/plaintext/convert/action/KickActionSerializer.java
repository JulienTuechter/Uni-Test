package de.fhmaze.plaintext.convert.action;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.KickAction;
import de.fhmaze.engine.common.Direction;

public class KickActionSerializer implements Serializer<KickAction> {
    @Override
    public String serialize(KickAction kickAction) {
        Direction direction = kickAction.direction();
        return "kick " + direction.toString().toLowerCase();
    }
}
