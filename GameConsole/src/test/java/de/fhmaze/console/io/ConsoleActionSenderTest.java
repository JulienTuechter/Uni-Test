package de.fhmaze.console.io;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import de.fhmaze.communication.convert.Serializer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.common.Direction;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsoleActionSenderTest {
    private ConsoleActionSender sender;
    private ByteArrayOutputStream buffer;

    @Mock
    private Serializer<Action> actionSerializer;

    @BeforeEach
    void setUp() {
        buffer = new ByteArrayOutputStream();
        sender = new ConsoleActionSender(new PrintStream(buffer), actionSerializer);
    }

    @Test
    void testSendAction() {
        // Arrange
        Action action = new GoAction(Direction.NORTH);
        when(actionSerializer.serialize(action))
            .thenReturn("SerializedAction");
        String expected = "SerializedAction";

        // Act
        sender.sendAction(action);
        String actual = buffer.toString().trim();

        // Assert
        assertEquals(expected, actual);
    }
}
