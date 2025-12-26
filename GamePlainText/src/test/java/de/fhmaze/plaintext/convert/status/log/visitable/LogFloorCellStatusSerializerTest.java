package de.fhmaze.plaintext.convert.status.log.visitable;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.status.visitable.FloorCellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogFloorCellStatusSerializerTest {
    private LogFloorCellStatusSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new LogFloorCellStatusSerializer();
    }

    @Test
    void testSerialize() {
        FloorCellStatus cellStatus = new FloorCellStatus();
        String expected = "  ";

        String data = serializer.serialize(cellStatus);

        assertEquals(expected, data);
    }
}
