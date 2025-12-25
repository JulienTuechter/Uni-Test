package de.fhmaze.plaintext.convert.status.log;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.status.UnknownCellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogUnknownCellStatusSerializerTest {
    private LogUnknownCellStatusSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new LogUnknownCellStatusSerializer();
    }

    @Test
    void testSerialize() {
        UnknownCellStatus cellStatus = new UnknownCellStatus();
        String expected = "??";

        String data = serializer.serialize(cellStatus);

        assertEquals(expected, data);
    }
}
