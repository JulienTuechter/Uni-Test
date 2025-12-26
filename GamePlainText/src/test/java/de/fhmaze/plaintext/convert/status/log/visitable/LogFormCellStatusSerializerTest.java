package de.fhmaze.plaintext.convert.status.log.visitable;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.status.visitable.FormCellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogFormCellStatusSerializerTest {
    private static final int PLAYER_ID = 1;
    private static final int FORM_ID = 2;
    private LogFormCellStatusSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new LogFormCellStatusSerializer();
    }

    @Test
    void testSerialize() {
        FormCellStatus cellStatus = new FormCellStatus(PLAYER_ID, FORM_ID);
        String expected = "" + (char) (FORM_ID + 64) + PLAYER_ID;

        String data = serializer.serialize(cellStatus);

        assertEquals(expected, data);
    }
}
