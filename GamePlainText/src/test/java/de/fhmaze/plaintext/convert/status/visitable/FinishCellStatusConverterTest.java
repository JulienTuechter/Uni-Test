package de.fhmaze.plaintext.convert.status.visitable;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.status.visitable.FinishCellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FinishCellStatusConverterTest {
    private static final int PLAYER_ID = 1;
    private static final int FORM_COUNT = 2;
    private static final int ENEMY_DISTANCE = 3;
    private FinishCellStatusConverter converter;

    @BeforeEach
    void setUp() {
        converter = new FinishCellStatusConverter();
    }

    @Test
    void testSerialize() {
        FinishCellStatus cellStatus = new FinishCellStatus(PLAYER_ID, FORM_COUNT);
        String expected = "FINISH " + PLAYER_ID + " " + FORM_COUNT;

        String data = converter.serialize(cellStatus);

        assertEquals(expected, data);
    }

    @Test
    void testSerializeHasEnemy() {
        FinishCellStatus cellStatus = new FinishCellStatus(PLAYER_ID, FORM_COUNT, 0);
        String expected = "FINISH " + PLAYER_ID + " " + FORM_COUNT + " !";

        String data = converter.serialize(cellStatus);

        assertEquals(expected, data);
    }

    @Test
    void testSerializeHasEnemyInSight() {
        FinishCellStatus cellStatus = new FinishCellStatus(PLAYER_ID, FORM_COUNT, ENEMY_DISTANCE);
        String expected = "FINISH " + PLAYER_ID + " " + FORM_COUNT + " !" + ENEMY_DISTANCE;

        String data = converter.serialize(cellStatus);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "FINISH " + PLAYER_ID + " " + FORM_COUNT;

        FinishCellStatus cellStatus = converter.parse(data);

        assertNotNull(cellStatus);
        assertEquals(PLAYER_ID, cellStatus.getPlayerId());
        assertEquals(FORM_COUNT, cellStatus.getFormCount());
    }

    @Test
    void testParseHasEnemy() {
        String data = "FINISH " + PLAYER_ID + " " + FORM_COUNT + " !";

        FinishCellStatus cellStatus = converter.parse(data);

        assertNotNull(cellStatus);
        assertEquals(PLAYER_ID, cellStatus.getPlayerId());
        assertEquals(FORM_COUNT, cellStatus.getFormCount());
        assertEquals(0, cellStatus.getEnemyDistance());
    }

    @Test
    void testParseHasEnemyInSight() {
        String data = "FINISH " + PLAYER_ID + " " + FORM_COUNT + " !" + ENEMY_DISTANCE;

        FinishCellStatus cellStatus = converter.parse(data);

        assertNotNull(cellStatus);
        assertEquals(PLAYER_ID, cellStatus.getPlayerId());
        assertEquals(FORM_COUNT, cellStatus.getFormCount());
        assertEquals(ENEMY_DISTANCE, cellStatus.getEnemyDistance());
    }
}
