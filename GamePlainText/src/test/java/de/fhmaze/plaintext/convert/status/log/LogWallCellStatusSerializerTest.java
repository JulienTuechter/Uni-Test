package de.fhmaze.plaintext.convert.status.log;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.status.WallCellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogWallCellStatusSerializerTest {
    private LogWallCellStatusSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new LogWallCellStatusSerializer();
    }

    @Test
    void testSerialize() {
        WallCellStatus cellStatus = new WallCellStatus();
        String expected = "##";

        String data = serializer.serialize(cellStatus);

        assertEquals(expected, data);
    }
}
