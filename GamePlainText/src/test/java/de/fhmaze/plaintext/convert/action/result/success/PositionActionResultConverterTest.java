package de.fhmaze.plaintext.convert.action.result.success;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.success.PositionActionResult;
import de.fhmaze.engine.common.Position;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class PositionActionResultConverterTest {
    private static final Random RANDOM = new Random();
    private PositionActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new PositionActionResultConverter();
    }

    @ParameterizedTest
    @MethodSource("randomPositions")
    void testSerialize(Position position) {
        PositionActionResult actionResult = new PositionActionResult(position);
        String expected = "OK " + position.x() + " " + position.y();

        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @ParameterizedTest
    @MethodSource("randomPositions")
    void testParse(Position position) {
        String data = "OK " + position.x() + " " + position.y();

        PositionActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
        assertEquals(position, actionResult.position());
    }

    static Stream<Position> randomPositions() {
        return Stream
            .generate(() -> new Position(RANDOM.nextInt(100), RANDOM.nextInt(100)))
            .limit(5);
    }
}
