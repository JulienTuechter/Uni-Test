package de.fhmaze.plaintext.convert.action;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.FinishAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FinishActionSerializerTest {
    private FinishActionSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new FinishActionSerializer();
    }

    @Test
    void testSerialize() {
        FinishAction action = new FinishAction();
        String expected = "finish";

        String data = serializer.serialize(action);

        assertEquals(expected, data);
    }
}
