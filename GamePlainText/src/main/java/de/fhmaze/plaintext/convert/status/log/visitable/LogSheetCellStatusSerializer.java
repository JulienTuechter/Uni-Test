package de.fhmaze.plaintext.convert.status.log.visitable;

import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.status.visitable.SheetCellStatus;

public class LogSheetCellStatusSerializer implements Serializer<SheetCellStatus> {
    @Override
    public String serialize(SheetCellStatus sheetStatus) {
        return "--";
    }
}
