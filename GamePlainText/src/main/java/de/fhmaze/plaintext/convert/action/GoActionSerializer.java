package de.fhmaze.plaintext.convert.action;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;

public class GoActionSerializer implements Serializer<Action> {
    @Override
    public String serialize(Action action) {
        GoAction goAction = (GoAction) action;
        return "go " + goAction.direction().toString().toLowerCase();
    }
}
