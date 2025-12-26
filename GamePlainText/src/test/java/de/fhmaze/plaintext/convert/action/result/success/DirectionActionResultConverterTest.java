package de.fhmaze.plaintext.convert.action.result.success;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.success.DirectionActionResult;
import de.fhmaze.engine.common.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class DirectionActionResultConverterTest {
    private DirectionActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DirectionActionResultConverter();
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testSerialize(Direction direction) {
        DirectionActionResult actionResult = new DirectionActionResult(direction);
        String expected = "OK " + direction;

        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testParse(Direction direction) {
        String data = "OK " + direction;

        DirectionActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
        assertEquals(direction, actionResult.direction());
    }
}
