package de.fhmaze.plaintext.convert.action.result.failure;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.failure.EmptyActionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmptyActionResultConverterTest {
    private EmptyActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new EmptyActionResultConverter();
    }

    @Test
    void testSerialize() {
        EmptyActionResult actionResult = new EmptyActionResult();

        String expected = "NOK EMPTY";
        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "NOK EMPTY";

        EmptyActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
    }
}
