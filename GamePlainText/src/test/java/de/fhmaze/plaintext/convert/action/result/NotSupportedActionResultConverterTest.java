package de.fhmaze.plaintext.convert.action.result;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.NotSupportedActionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotSupportedActionResultConverterTest {
    private NotSupportedActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new NotSupportedActionResultConverter();
    }

    @Test
    void testSerialize() {
        NotSupportedActionResult actionResult = new NotSupportedActionResult();

        String expected = "NOK NOTSUPPORTED";
        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "NOK NOTSUPPORTED";

        NotSupportedActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
    }
}
