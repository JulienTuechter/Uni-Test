package de.fhmaze.plaintext.convert.action.result.success;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.success.FormActionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormActionResultConverterTest {
    private FormActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new FormActionResultConverter();
    }

    @Test
    void testSerialize() {
        FormActionResult actionResult = new FormActionResult();

        String expected = "OK FORM";
        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "OK FORM";

        FormActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
    }
}
