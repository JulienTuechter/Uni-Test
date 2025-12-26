package de.fhmaze.plaintext.convert.action.result.failure;

import static org.junit.jupiter.api.Assertions.*;
import de.fhmaze.engine.action.result.failure.BlockedActionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlockedActionResultConverterTest {
    private BlockedActionResultConverter converter;

    @BeforeEach
    void setUp() {
        converter = new BlockedActionResultConverter();
    }

    @Test
    void testSerialize() {
        BlockedActionResult actionResult = new BlockedActionResult();

        String expected = "NOK BLOCKED";
        String data = converter.serialize(actionResult);

        assertEquals(expected, data);
    }

    @Test
    void testParse() {
        String data = "NOK BLOCKED";

        BlockedActionResult actionResult = converter.parse(data);

        assertNotNull(actionResult);
    }
}
