package de.fhmaze.plaintext.convert.action;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.KickAction;

public class KickActionSerializer implements Serializer<Action> {
    @Override
    public String serialize(Action action) {
        KickAction kickAction = (KickAction) action;
        return "kick " + kickAction.direction().toString().toLowerCase();
    }
}
