import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Message {

    public String messageID;
    public String recipient;
    public String messageText;
    public String messageHash;
    public int messageNumber;

    public static ArrayList<String> sentMessages = new ArrayList<String>();
    public static int totalMessagesSent = 0;

    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    public String generateMessageID() {
        Random rand = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id = id + rand.nextInt(10);
        }
        return id;
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient.length() <= 13 && recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        String[] words = messageText.trim().split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String hash = messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String SentMessage(int choice) {
        if (choice == 1) {
            totalMessagesSent++;
            String record = "Message ID: " + messageID + " | Hash: " + messageHash + " | Recipient: " + recipient + " | Message: " + messageText;
            sentMessages.add(record);
            return "Message successfully sent.";
        }
        if (choice == 0) {
            return "Press 0 to delete the message.";
        }
        if (choice == 2) {
            storeMessage();
            return "Message successfully stored.";
        }
        return "Invalid choice.";
    }

    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        String allMessages = "";
        for (int i = 0; i < sentMessages.size(); i++) {
            allMessages = allMessages + sentMessages.get(i) + "\n";
        }
        return allMessages;
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void storeMessage() {
        try {
            JSONObject msgObj = new JSONObject();
            msgObj.put("MessageID", messageID);
            msgObj.put("MessageHash", messageHash);
            msgObj.put("Recipient", recipient);
            msgObj.put("Message", messageText);

            JSONArray jsonArray = new JSONArray();

            File file = new File("storedMessages.json");
            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get("storedMessages.json")));
                jsonArray = new JSONArray(content);
            }

            jsonArray.put(msgObj);
            Files.write(Paths.get("storedMessages.json"), jsonArray.toString(2).getBytes());

        } catch (Exception e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }
}