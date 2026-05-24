import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        assertTrue(msg.messageText.length() <= 250);
    }

    @Test
    public void testMessageLengthFailure() {
        String longMessage = "A".repeat(260);
        assertTrue(longMessage.length() > 250);
    }

    @Test
    public void testRecipientSuccess() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        Message msg = new Message("08575975889", "Hi Keegan, did you receive the payment?", 2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    @Test
    public void testMessageHashCorrect() {
        Message msg = new Message("+27718693002", "Hi tonight", 1);
        assertEquals(msg.messageID.substring(0, 2) + ":1:HITONIGHT", msg.messageHash);
    }

    @Test
    public void testMessageIDCreated() {
        Message msg = new Message("+27718693002", "Hello World", 1);
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testSendMessage() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }

    @Test
    public void testDisregardMessage() {
        Message msg = new Message("+27718693002", "Hello World", 1);
        assertEquals("Press 0 to delete the message.", msg.SentMessage(0));
    }

    @Test
    public void testStoreMessage() {
        Message msg = new Message("+27718693002", "Hello World", 1);
        assertEquals("Message successfully stored.", msg.SentMessage(2));
    }
}