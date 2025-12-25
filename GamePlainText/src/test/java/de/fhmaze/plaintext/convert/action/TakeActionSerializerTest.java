package de.fhmaze.plaintext.convert.action;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.TakeAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TakeActionSerializerTest {
    private TakeActionSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new TakeActionSerializer();
    }

    @Test
    void testSerialize() {
        TakeAction action = new TakeAction();
        String expected = "take";

        String data = serializer.serialize(action);

        assertEquals(expected, data);
    }
}
