package de.fhmaze.plaintext.convert.action;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.Action;

public class TakeActionSerializer implements Serializer<Action> {
    @Override
    public String serialize(Action action) {
        return "take";
    }
}
