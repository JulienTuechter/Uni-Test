package de.fhmaze.plaintext.protocol;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.communication.convert.Parser;
import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.communication.protocol.ProtocolFactory;
import de.fhmaze.communication.registry.ConverterRegistry;
import de.fhmaze.communication.registry.SerializerRegistry;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.FinishAction;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.KickAction;
import de.fhmaze.engine.action.PositionAction;
import de.fhmaze.engine.action.PutAction;
import de.fhmaze.engine.action.TakeAction;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.BlockedActionResult;
import de.fhmaze.engine.action.result.DirectionActionResult;
import de.fhmaze.engine.action.result.EmptyActionResult;
import de.fhmaze.engine.action.result.FormActionResult;
import de.fhmaze.engine.action.result.NotOkayActionResult;
import de.fhmaze.engine.action.result.NotSupportedActionResult;
import de.fhmaze.engine.action.result.NotYoursActionResult;
import de.fhmaze.engine.action.result.OkayActionResult;
import de.fhmaze.engine.action.result.PositionActionResult;
import de.fhmaze.engine.action.result.SheetActionResult;
import de.fhmaze.engine.action.result.TakingActionResult;
import de.fhmaze.engine.action.result.TalkingActionResult;
import de.fhmaze.engine.action.result.WrongOrderActionResult;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.FinishCellStatus;
import de.fhmaze.engine.status.FloorCellStatus;
import de.fhmaze.engine.status.FormCellStatus;
import de.fhmaze.engine.status.SheetCellStatus;
import de.fhmaze.engine.status.UnknownCellStatus;
import de.fhmaze.engine.status.WallCellStatus;
import de.fhmaze.plaintext.convert.action.FinishActionSerializer;
import de.fhmaze.plaintext.convert.action.GoActionSerializer;
import de.fhmaze.plaintext.convert.action.KickActionSerializer;
import de.fhmaze.plaintext.convert.action.PositionActionSerializer;
import de.fhmaze.plaintext.convert.action.PutActionSerializer;
import de.fhmaze.plaintext.convert.action.TakeActionSerializer;
import de.fhmaze.plaintext.convert.action.result.BlockedActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.DirectionActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.EmptyActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.FormActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.NotOkayActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.NotSupportedActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.NotYoursActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.OkayActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.PositionActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.SheetActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.TakingActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.TalkingActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.WrongOrderActionResultConverter;
import de.fhmaze.plaintext.convert.status.FinishCellStatusConverter;
import de.fhmaze.plaintext.convert.status.FloorCellStatusConverter;
import de.fhmaze.plaintext.convert.status.FormCellStatusConverter;
import de.fhmaze.plaintext.convert.status.SheetCellStatusConverter;
import de.fhmaze.plaintext.convert.status.WallCellStatusConverter;
import de.fhmaze.plaintext.convert.status.log.LogFinishCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.log.LogFloorCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.log.LogFormCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.log.LogSheetCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.log.LogUnknownCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.log.LogWallCellStatusSerializer;
import java.util.function.Function;

public class PlainTextProtocolFactory implements ProtocolFactory {
    @Override
    public Serializer<Action> createActionSerializer() {
        Function<Action, Serializer<Action>> serializerProvider = action -> {
            return switch (action) {
                case GoAction _ -> new GoActionSerializer();
                case KickAction _ -> new KickActionSerializer();
                case PositionAction _ -> new PositionActionSerializer();
                case TakeAction _ -> new TakeActionSerializer();
                case PutAction _ -> new PutActionSerializer();
                case FinishAction _ -> new FinishActionSerializer();
                default -> null;
            };
        };

        return new SerializerRegistry<>(serializerProvider);
    }

    @Override
    public Converter<ActionResult> createActionResultConverter() {
        Function<ActionResult, Serializer<ActionResult>> serializerProvider = actionResult -> {
            return switch (actionResult) {
                case DirectionActionResult _ -> new DirectionActionResultConverter();
                case PositionActionResult _ -> new PositionActionResultConverter();
                case FormActionResult _ -> new FormActionResultConverter();
                case SheetActionResult _ -> new SheetActionResultConverter();
                case OkayActionResult _ -> new OkayActionResultConverter();
                case BlockedActionResult _ -> new BlockedActionResultConverter();
                case WrongOrderActionResult _ -> new WrongOrderActionResultConverter();
                case NotYoursActionResult _ -> new NotYoursActionResultConverter();
                case EmptyActionResult _ -> new EmptyActionResultConverter();
                case TalkingActionResult _ -> new TalkingActionResultConverter();
                case TakingActionResult _ -> new TakingActionResultConverter();
                case NotSupportedActionResult _ -> new NotSupportedActionResultConverter();
                case NotOkayActionResult _ -> new NotOkayActionResultConverter();
            };
        };

        Function<String, Parser<ActionResult>> parserProvider = data -> {
            String[] parts = data.split(" ");
            return switch (parts[0]) {
                case "OK" -> switch (parts.length) {
                    case 1 -> new OkayActionResultConverter();
                    case 2 -> switch (parts[1]) {
                        case "FORM" -> new FormActionResultConverter();
                        case "SHEET" -> new SheetActionResultConverter();
                        default -> new DirectionActionResultConverter();
                    };
                    case 3 -> new PositionActionResultConverter();
                    default -> new NotSupportedActionResultConverter();
                };
                case "NOK" -> switch (parts.length) {
                    case 1 -> new NotOkayActionResultConverter();
                    case 2 -> switch (parts[1]) {
                        case "BLOCKED" -> new BlockedActionResultConverter();
                        case "WRONGORDER" -> new WrongOrderActionResultConverter();
                        case "NOTYOURS" -> new NotYoursActionResultConverter();
                        case "EMPTY" -> new EmptyActionResultConverter();
                        case "TALKING" -> new TalkingActionResultConverter();
                        case "TAKING" -> new TakingActionResultConverter();
                        default -> new NotSupportedActionResultConverter();
                    };
                    default -> new NotSupportedActionResultConverter();
                };
                default -> null;
            };
        };

        return new ConverterRegistry<>(serializerProvider, parserProvider);
    }

    @Override
    public Converter<CellStatus> createCellStatusConverter() {
        Function<CellStatus, Serializer<CellStatus>> serializerProvider = cellStatus -> {
            return switch (cellStatus) {
                case WallCellStatus _ -> new WallCellStatusConverter();
                case FloorCellStatus _ -> new FloorCellStatusConverter();
                case FormCellStatus _ -> new FormCellStatusConverter();
                case SheetCellStatus _ -> new SheetCellStatusConverter();
                case FinishCellStatus _ -> new FinishCellStatusConverter();
                default -> null;
            };
        };

        Function<String, Parser<CellStatus>> parserProvider = data -> {
            String[] parts = data.split(" ");
            return switch (parts[0]) {
                case "WALL" -> new WallCellStatusConverter();
                case "FLOOR" -> new FloorCellStatusConverter();
                case "FORM" -> new FormCellStatusConverter();
                case "SHEET" -> new SheetCellStatusConverter();
                case "FINISH" -> new FinishCellStatusConverter();
                default -> null;
            };
        };

        return new ConverterRegistry<>(serializerProvider, parserProvider);
    }

    @Override
    public Serializer<CellStatus> createLogCellStatusSerializer() {
        Function<CellStatus, Serializer<CellStatus>> serializerProvider = cellStatus -> {
            return switch (cellStatus) {
                case WallCellStatus _ -> new LogWallCellStatusSerializer();
                case FloorCellStatus _ -> new LogFloorCellStatusSerializer();
                case FormCellStatus _ -> new LogFormCellStatusSerializer();
                case SheetCellStatus _ -> new LogSheetCellStatusSerializer();
                case FinishCellStatus _ -> new LogFinishCellStatusSerializer();
                case UnknownCellStatus _ -> new LogUnknownCellStatusSerializer();
                default -> null;
            };
        };

        return new SerializerRegistry<>(serializerProvider);
    }
}
