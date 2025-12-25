package de.fhmaze.plaintext.convert.status;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.status.FloorCellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FloorCellStatusConverterTest {
    private static final int ENEMY_DISTANCE = 3;
    private FloorCellStatusConverter converter;

    @BeforeEach
    void setUp() {
        converter = new FloorCellStatusConverter();
    }

    @Test
    void testSerialize() {
        FloorCellStatus cellStatus = new FloorCellStatus();
        String expected = "FLOOR";

        String data = converter.serialize(cellStatus);

        assertEquals(expected, data);
    }

    @Test
    void testSerializeHasEnemy() {
        FloorCellStatus cellStatus = new FloorCellStatus(0);
        String expected = "FLOOR !";

        String data = converter.serialize(cellStatus);

        assertEquals(expected, data);
    }

    @Test
    void testSerializeHasEnemyInSight() {
        FloorCellStatus cellStatus = new FloorCellStatus(ENEMY_DISTANCE);
        String expected = "FLOOR !" + ENEMY_DISTANCE;

        String data = converter.serialize(cellStatus);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "FLOOR";

        FloorCellStatus cellStatus = converter.parse(data);

        assertNotNull(cellStatus);
    }

    @Test
    void testParseHasEnemy() {
        String data = "FLOOR !";

        FloorCellStatus cellStatus = converter.parse(data);

        assertNotNull(cellStatus);
        assertEquals(0, cellStatus.getEnemyDistance());
    }

    @Test
    void testParseHasEnemyInSight() {
        String data = "FLOOR !" + ENEMY_DISTANCE;

        FloorCellStatus cellStatus = converter.parse(data);

        assertNotNull(cellStatus);
        assertEquals(ENEMY_DISTANCE, cellStatus.getEnemyDistance());
    }
}
