package de.fhmaze.plaintext.convert.action.result;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.NotOkayActionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotOkayActionResultConverterTest {
    private NotOkayActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new NotOkayActionResultConverter();
    }

    @Test
    void testSerialize() {
        NotOkayActionResult actionResult = new NotOkayActionResult();

        String expected = "NOK";
        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "NOK";

        NotOkayActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
    }
}
