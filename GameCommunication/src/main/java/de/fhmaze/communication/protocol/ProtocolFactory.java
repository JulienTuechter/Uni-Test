package de.fhmaze.communication.protocol;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.status.CellStatus;

// Entwurfsmuster (Erzeugung): Abstract Factory
public interface ProtocolFactory {
    Serializer<Action> createActionSerializer();
    Converter<ActionResult> createActionResultConverter();
    Converter<CellStatus> createCellStatusConverter();
    Serializer<CellStatus> createLogCellStatusSerializer();
}
