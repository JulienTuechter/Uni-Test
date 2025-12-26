package de.fhmaze.plaintext.convert.action;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.PutAction;

public class PutActionSerializer implements Serializer<PutAction> {
    @Override
    public String serialize(PutAction putAction) {
        return "put";
    }
}
