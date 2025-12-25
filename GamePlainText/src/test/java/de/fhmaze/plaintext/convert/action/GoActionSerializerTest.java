package de.fhmaze.plaintext.convert.action;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.common.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class GoActionSerializerTest {
    private GoActionSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new GoActionSerializer();
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testSerialize(Direction direction) {
        GoAction action = new GoAction(direction);
        String expected = "go " + direction.toString().toLowerCase();

        String data = serializer.serialize(action);

        assertEquals(expected, data);
    }
}
