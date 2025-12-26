package de.fhmaze.plaintext.convert.status.log.visitable;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.status.visitable.FinishCellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogFinishCellStatusSerializerTest {
    private static final int PLAYER_ID = 1;
    private static final int FORM_COUNT = 2;
    private LogFinishCellStatusSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new LogFinishCellStatusSerializer();
    }

    @Test
    void testSerialize() {
        FinishCellStatus cellStatus = new FinishCellStatus(PLAYER_ID, FORM_COUNT);
        String expected = "!" + PLAYER_ID;

        String data = serializer.serialize(cellStatus);

        assertEquals(expected, data);
    }
}
