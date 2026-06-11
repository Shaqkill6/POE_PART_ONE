import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageTest {

    @BeforeEach
    public void setUp() {
        Message.sentMessages.clear();
        Message.disregardedMessages.clear();
        Message.messageHashes.clear();
        Message.messageIDs.clear();
        Message.totalMessagesSent = 0;
        try {
            Files.deleteIfExists(Paths.get("storedMessages.json"));
        } catch (Exception e) {
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            Files.deleteIfExists(Paths.get("storedMessages.json"));
        } catch (Exception e) {
        }
    }

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

    @Test
    public void testSentMessagesArrayPopulated() {
        new Message("+27834557896", "Did you get the cake?", 1).SentMessage(1);
        new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2).SentMessage(2);
        new Message("+27834484567", "Yohoooo, I am at your gate.", 3).SentMessage(0);
        new Message("0838884567", "It is dinner time!", 4).SentMessage(1);

        boolean foundCake = false;
        boolean foundDinner = false;

        for (int i = 0; i < Message.sentMessages.size(); i++) {
            if (Message.sentMessages.get(i).contains("Did you get the cake?")) {
                foundCake = true;
            }
            if (Message.sentMessages.get(i).contains("It is dinner time!")) {
                foundDinner = true;
            }
        }

        assertTrue(foundCake);
        assertTrue(foundDinner);
    }

    @Test
    public void testLongestStoredMessage() {
        new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2).SentMessage(2);
        new Message("+27838884567", "Ok, I am leaving without you.", 5).SentMessage(2);

        assertEquals("Where are you? You are late! I have asked you to be on time.", Message.getLongestStoredMessage());
    }

    @Test
    public void testSearchByMessageID() {
        Message msg4 = new Message("0838884567", "It is dinner time!", 4);
        msg4.SentMessage(1);

        String result = Message.searchByMessageID(msg4.messageID);
        assertTrue(result.contains("It is dinner time!"));
    }

    @Test
    public void testSearchByRecipient() {
        new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2).SentMessage(2);
        new Message("+27838884567", "Ok, I am leaving without you.", 5).SentMessage(2);

        ArrayList<JSONObject> stored = Message.loadStoredMessages();

        boolean foundFirst = false;
        boolean foundSecond = false;

        for (int i = 0; i < stored.size(); i++) {
            if (stored.get(i).getString("Recipient").equals("+27838884567")) {
                if (stored.get(i).getString("Message").equals("Where are you? You are late! I have asked you to be on time.")) {
                    foundFirst = true;
                }
                if (stored.get(i).getString("Message").equals("Ok, I am leaving without you.")) {
                    foundSecond = true;
                }
            }
        }

        assertTrue(foundFirst);
        assertTrue(foundSecond);
    }

    @Test
    public void testDeleteByHash() {
        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2);
        msg2.SentMessage(2);

        String result = Message.deleteByHash(msg2.messageHash);
        assertEquals("Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
    }

    @Test
    public void testDisplayReport() {
        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2);
        msg2.SentMessage(2);

        ArrayList<JSONObject> stored = Message.loadStoredMessages();
        boolean found = false;

        for (int i = 0; i < stored.size(); i++) {
            if (stored.get(i).getString("Message").equals("Where are you? You are late! I have asked you to be on time.")) {
                found = true;
            }
        }

        assertTrue(found);
    }
}