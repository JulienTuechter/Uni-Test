package de.fhmaze.plaintext.convert.action;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.FinishAction;

public class FinishActionSerializer implements Serializer<FinishAction> {
    @Override
    public String serialize(FinishAction finishAction) {
        return "finish";
    }
}
