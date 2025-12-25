package de.fhmaze.plaintext.convert.action.result;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.OkayActionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OkayActionResultConverterTest {
    private OkayActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new OkayActionResultConverter();
    }

    @Test
    void testSerialize() {
        OkayActionResult actionResult = new OkayActionResult();

        String expected = "OK";
        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "OK";

        OkayActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
    }
}
