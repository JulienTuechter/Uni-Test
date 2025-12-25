package de.fhmaze.console.io;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.io.ActionSender;
import java.io.PrintStream;

public class ConsoleActionSender implements ActionSender {
    private final PrintStream out;
    private final Serializer<Action> actionSerializer;

    public ConsoleActionSender(PrintStream out, Serializer<Action> actionSerializer) {
        this.out = out;
        this.actionSerializer = actionSerializer;
    }

    @Override
    public void sendAction(Action action) {
        String data = actionSerializer.serialize(action);
        out.println(data);
    }
}
