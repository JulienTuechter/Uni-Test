package de.fhmaze.plaintext.convert.status.visitable;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.status.visitable.FormCellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormCellStatusConverterTest {
    private static final int PLAYER_ID = 1;
    private static final int FORM_ID = 2;
    private static final int ENEMY_DISTANCE = 3;
    private FormCellStatusConverter converter;

    @BeforeEach
    void setUp() {
        converter = new FormCellStatusConverter();
    }

    @Test
    void testSerialize() {
        FormCellStatus cellStatus = new FormCellStatus(PLAYER_ID, FORM_ID);
        String expected = "FORM " + PLAYER_ID + " " + FORM_ID;

        String data = converter.serialize(cellStatus);

        assertEquals(expected, data);
    }

    @Test
    void testSerializeHasEnemy() {
        FormCellStatus cellStatus = new FormCellStatus(PLAYER_ID, FORM_ID, 0);
        String expected = "FORM " + PLAYER_ID + " " + FORM_ID + " !";

        String data = converter.serialize(cellStatus);

        assertEquals(expected, data);
    }

    @Test
    void testSerializeHasEnemyInSight() {
        FormCellStatus cellStatus = new FormCellStatus(PLAYER_ID, FORM_ID, ENEMY_DISTANCE);
        String expected = "FORM " + PLAYER_ID + " " + FORM_ID + " !" + ENEMY_DISTANCE;

        String data = converter.serialize(cellStatus);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "FORM " + PLAYER_ID + " " + FORM_ID;

        FormCellStatus cellStatus = converter.parse(data);

        assertNotNull(cellStatus);
        assertEquals(PLAYER_ID, cellStatus.getPlayerId());
        assertEquals(FORM_ID, cellStatus.getFormId());
    }

    @Test
    void testParseHasEnemy() {
        String data = "FORM " + PLAYER_ID + " " + FORM_ID + " !";

        FormCellStatus cellStatus = converter.parse(data);

        assertNotNull(cellStatus);
        assertEquals(PLAYER_ID, cellStatus.getPlayerId());
        assertEquals(FORM_ID, cellStatus.getFormId());
        assertEquals(0, cellStatus.getEnemyDistance());
    }

    @Test
    void testParseHasEnemyInSight() {
        String data = "FORM " + PLAYER_ID + " " + FORM_ID + " !" + ENEMY_DISTANCE;

        FormCellStatus cellStatus = converter.parse(data);

        assertNotNull(cellStatus);
        assertEquals(PLAYER_ID, cellStatus.getPlayerId());
        assertEquals(FORM_ID, cellStatus.getFormId());
        assertEquals(ENEMY_DISTANCE, cellStatus.getEnemyDistance());
    }
}
