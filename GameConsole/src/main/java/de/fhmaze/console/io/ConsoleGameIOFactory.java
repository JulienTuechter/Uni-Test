package de.fhmaze.console.io;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.communication.protocol.ProtocolFactory;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.io.ActionSender;
import de.fhmaze.engine.io.GameIOFactory;
import de.fhmaze.engine.io.GameInputReader;
import de.fhmaze.engine.io.GameLogger;
import de.fhmaze.engine.status.CellStatus;
import java.io.InputStream;
import java.io.PrintStream;

public class ConsoleGameIOFactory implements GameIOFactory {
    private final InputStream in = System.in;
    private final PrintStream out = System.out;
    private final PrintStream log = System.err;
    private final Serializer<Action> actionSerializer;
    private final Converter<ActionResult> actionResultConverter;
    private final Converter<CellStatus> cellStatusConverter;
    private final Serializer<CellStatus> logCellStatusSerializer;

    public ConsoleGameIOFactory(ProtocolFactory factory) {
        actionSerializer = factory.createActionSerializer();
        actionResultConverter = factory.createActionResultConverter();
        cellStatusConverter = factory.createCellStatusConverter();
        logCellStatusSerializer = factory.createLogCellStatusSerializer();
    }

    @Override
    public GameInputReader createGameInputReader() {
        return new ConsoleGameInputReader(in, actionResultConverter, cellStatusConverter);
    }

    @Override
    public ActionSender createActionSender() {
        return new ConsoleActionSender(out, actionSerializer);
    }

    @Override
    public GameLogger createGameLogger() {
        return ConsoleGameLogger.getInstance(log, actionResultConverter, cellStatusConverter, logCellStatusSerializer);
    }
}
