package de.fhmaze.plaintext.convert.action.result.failure;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.failure.TalkingActionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TalkingActionResultConverterTest {
    private TalkingActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new TalkingActionResultConverter();
    }

    @Test
    void testSerialize() {
        TalkingActionResult actionResult = new TalkingActionResult();

        String expected = "NOK TALKING";
        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "NOK TALKING";

        TalkingActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
    }
}
