package de.fhmaze.plaintext.convert.action;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.PositionAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionActionSerializerTest {
    private PositionActionSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new PositionActionSerializer();
    }

    @Test
    void testSerialize() {
        PositionAction action = new PositionAction();
        String expected = "position";

        String data = serializer.serialize(action);

        assertEquals(expected, data);
    }
}
