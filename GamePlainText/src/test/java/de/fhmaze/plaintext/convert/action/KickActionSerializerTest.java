package de.fhmaze.plaintext.convert.action;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.KickAction;
import de.fhmaze.engine.common.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class KickActionSerializerTest {
    private KickActionSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new KickActionSerializer();
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testSerialize(Direction direction) {
        KickAction action = new KickAction(direction);
        String expected = "kick " + direction.toString().toLowerCase();

        String data = serializer.serialize(action);

        assertEquals(expected, data);
    }
}
