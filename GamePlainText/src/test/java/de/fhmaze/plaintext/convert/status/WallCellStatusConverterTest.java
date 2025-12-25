package de.fhmaze.plaintext.convert.status;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.status.WallCellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WallCellStatusConverterTest {
    private WallCellStatusConverter converter;

    @BeforeEach
    void setUp() {
        converter = new WallCellStatusConverter();
    }

    @Test
    void testSerialize() {
        WallCellStatus cellStatus = new WallCellStatus();
        String expected = "WALL";

        String data = converter.serialize(cellStatus);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "WALL";

        WallCellStatus cellStatus = converter.parse(data);

        assertNotNull(cellStatus);
    }
}
