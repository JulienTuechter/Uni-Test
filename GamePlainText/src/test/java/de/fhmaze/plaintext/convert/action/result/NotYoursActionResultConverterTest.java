package de.fhmaze.plaintext.convert.action.result;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.NotYoursActionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotYoursActionResultConverterTest {
    private NotYoursActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new NotYoursActionResultConverter();
    }

    @Test
    void testSerialize() {
        NotYoursActionResult actionResult = new NotYoursActionResult();

        String expected = "NOK NOTYOURS";
        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "NOK NOTYOURS";

        NotYoursActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
    }
}
