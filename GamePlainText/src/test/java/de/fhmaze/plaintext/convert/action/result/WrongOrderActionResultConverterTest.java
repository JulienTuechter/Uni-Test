package de.fhmaze.plaintext.convert.action.result;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.WrongOrderActionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WrongOrderActionResultConverterTest {
    private WrongOrderActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new WrongOrderActionResultConverter();
    }

    @Test
    void testSerialize() {
        WrongOrderActionResult actionResult = new WrongOrderActionResult();

        String expected = "NOK WRONGORDER";
        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "NOK WRONGORDER";

        WrongOrderActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
    }
}
