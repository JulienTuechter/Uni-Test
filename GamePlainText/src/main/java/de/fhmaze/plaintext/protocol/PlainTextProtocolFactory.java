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
import de.fhmaze.engine.action.result.failure.BlockedActionResult;
import de.fhmaze.engine.action.result.failure.EmptyActionResult;
import de.fhmaze.engine.action.result.failure.NotOkayActionResult;
import de.fhmaze.engine.action.result.failure.NotSupportedActionResult;
import de.fhmaze.engine.action.result.failure.NotYoursActionResult;
import de.fhmaze.engine.action.result.failure.TakingActionResult;
import de.fhmaze.engine.action.result.failure.TalkingActionResult;
import de.fhmaze.engine.action.result.failure.WrongOrderActionResult;
import de.fhmaze.engine.action.result.success.DirectionActionResult;
import de.fhmaze.engine.action.result.success.FormActionResult;
import de.fhmaze.engine.action.result.success.OkayActionResult;
import de.fhmaze.engine.action.result.success.PositionActionResult;
import de.fhmaze.engine.action.result.success.SheetActionResult;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.status.UnknownCellStatus;
import de.fhmaze.engine.status.WallCellStatus;
import de.fhmaze.engine.status.visitable.FinishCellStatus;
import de.fhmaze.engine.status.visitable.FloorCellStatus;
import de.fhmaze.engine.status.visitable.FormCellStatus;
import de.fhmaze.engine.status.visitable.SheetCellStatus;
import de.fhmaze.plaintext.convert.action.FinishActionSerializer;
import de.fhmaze.plaintext.convert.action.GoActionSerializer;
import de.fhmaze.plaintext.convert.action.KickActionSerializer;
import de.fhmaze.plaintext.convert.action.PositionActionSerializer;
import de.fhmaze.plaintext.convert.action.PutActionSerializer;
import de.fhmaze.plaintext.convert.action.TakeActionSerializer;
import de.fhmaze.plaintext.convert.action.result.failure.BlockedActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.failure.EmptyActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.failure.NotOkayActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.failure.NotSupportedActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.failure.NotYoursActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.failure.TakingActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.failure.TalkingActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.failure.WrongOrderActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.success.DirectionActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.success.FormActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.success.OkayActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.success.PositionActionResultConverter;
import de.fhmaze.plaintext.convert.action.result.success.SheetActionResultConverter;
import de.fhmaze.plaintext.convert.status.WallCellStatusConverter;
import de.fhmaze.plaintext.convert.status.log.LogUnknownCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.log.LogWallCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.log.visitable.LogFinishCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.log.visitable.LogFloorCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.log.visitable.LogFormCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.log.visitable.LogSheetCellStatusSerializer;
import de.fhmaze.plaintext.convert.status.visitable.FinishCellStatusConverter;
import de.fhmaze.plaintext.convert.status.visitable.FloorCellStatusConverter;
import de.fhmaze.plaintext.convert.status.visitable.FormCellStatusConverter;
import de.fhmaze.plaintext.convert.status.visitable.SheetCellStatusConverter;
import java.util.function.Function;

public class PlainTextProtocolFactory implements ProtocolFactory {
    @Override
    public Serializer<Action> createActionSerializer() {
        Function<Action, Serializer<? extends Action>> serializerProvider = action -> {
            return switch (action) {
                case PositionAction _ -> new PositionActionSerializer();
                case GoAction _ -> new GoActionSerializer();
                case KickAction _ -> new KickActionSerializer();
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
        Function<ActionResult, Serializer<? extends ActionResult>> serializerProvider = actionResult -> {
            return switch (actionResult) {
                case OkayActionResult _ -> new OkayActionResultConverter();
                case PositionActionResult _ -> new PositionActionResultConverter();
                case DirectionActionResult _ -> new DirectionActionResultConverter();
                case FormActionResult _ -> new FormActionResultConverter();
                case SheetActionResult _ -> new SheetActionResultConverter();
                case NotOkayActionResult _ -> new NotOkayActionResultConverter();
                case BlockedActionResult _ -> new BlockedActionResultConverter();
                case WrongOrderActionResult _ -> new WrongOrderActionResultConverter();
                case NotYoursActionResult _ -> new NotYoursActionResultConverter();
                case EmptyActionResult _ -> new EmptyActionResultConverter();
                case TalkingActionResult _ -> new TalkingActionResultConverter();
                case TakingActionResult _ -> new TakingActionResultConverter();
                case NotSupportedActionResult _ -> new NotSupportedActionResultConverter();
                default -> null;
            };
        };

        Function<String, Parser<? extends ActionResult>> parserProvider = data -> {
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
                    default -> null;
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
                        case "NOTSUPPORTED" -> new NotSupportedActionResultConverter();
                        default -> null;
                    };
                    default -> null;
                };
                default -> null;
            };
        };

        return new ConverterRegistry<>(serializerProvider, parserProvider);
    }

    @Override
    public Converter<CellStatus> createCellStatusConverter() {
        Function<CellStatus, Serializer<? extends CellStatus>> serializerProvider = cellStatus -> {
            return switch (cellStatus) {
                case FloorCellStatus _ -> new FloorCellStatusConverter();
                case FormCellStatus _ -> new FormCellStatusConverter();
                case SheetCellStatus _ -> new SheetCellStatusConverter();
                case FinishCellStatus _ -> new FinishCellStatusConverter();
                case WallCellStatus _ -> new WallCellStatusConverter();
                default -> null;
            };
        };

        Function<String, Parser<? extends CellStatus>> parserProvider = data -> {
            String[] parts = data.split(" ");
            return switch (parts[0]) {
                case "FLOOR" -> new FloorCellStatusConverter();
                case "FORM" -> new FormCellStatusConverter();
                case "SHEET" -> new SheetCellStatusConverter();
                case "FINISH" -> new FinishCellStatusConverter();
                case "WALL" -> new WallCellStatusConverter();
                default -> null;
            };
        };

        return new ConverterRegistry<>(serializerProvider, parserProvider);
    }

    @Override
    public Serializer<CellStatus> createLogCellStatusSerializer() {
        Function<CellStatus, Serializer<? extends CellStatus>> serializerProvider = cellStatus -> {
            return switch (cellStatus) {
                case FloorCellStatus _ -> new LogFloorCellStatusSerializer();
                case FormCellStatus _ -> new LogFormCellStatusSerializer();
                case SheetCellStatus _ -> new LogSheetCellStatusSerializer();
                case FinishCellStatus _ -> new LogFinishCellStatusSerializer();
                case WallCellStatus _ -> new LogWallCellStatusSerializer();
                case UnknownCellStatus _ -> new LogUnknownCellStatusSerializer();
                default -> null;
            };
        };

        return new SerializerRegistry<>(serializerProvider);
    }
}
