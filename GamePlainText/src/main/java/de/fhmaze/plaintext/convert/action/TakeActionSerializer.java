package de.fhmaze.plaintext.convert.action;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.TakeAction;

public class TakeActionSerializer implements Serializer<TakeAction> {
    @Override
    public String serialize(TakeAction takeAction) {
        return "take";
    }
}
