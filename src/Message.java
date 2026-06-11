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
    public static String sender = "";

    public static ArrayList<String> sentMessages = new ArrayList<String>();
    public static ArrayList<String> disregardedMessages = new ArrayList<String>();
    public static ArrayList<String> messageHashes = new ArrayList<String>();
    public static ArrayList<String> messageIDs = new ArrayList<String>();
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
        if (messageText == null || messageText.trim().isEmpty()) {
            return "";
        }
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
            messageHashes.add(messageHash);
            messageIDs.add(messageID);
            return "Message successfully sent.";
        }
        if (choice == 0) {
            String record = "Message ID: " + messageID + " | Hash: " + messageHash + " | Recipient: " + recipient + " | Message: " + messageText;
            disregardedMessages.add(record);
            return "Press 0 to delete the message.";
        }
        if (choice == 2) {
            storeMessage();
            messageHashes.add(messageHash);
            messageIDs.add(messageID);
            return "Message successfully stored.";
        }
        return "Invalid choice.";
    }

    public static void printMessages() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages sent yet.");
        } else {
            for (int i = 0; i < sentMessages.size(); i++) {
                System.out.println(sentMessages.get(i));
            }
        }
    }
    public void storeMessage() {
        try {
            JSONObject msgObj = new JSONObject();
            msgObj.put("MessageID", messageID);
            msgObj.put("MessageHash", messageHash);
            msgObj.put("Recipient", recipient);
            msgObj.put("Sender", sender);
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

    public static ArrayList<JSONObject> loadStoredMessages() {
        ArrayList<JSONObject> stored = new ArrayList<JSONObject>();
        try {
            File file = new File("storedMessages.json");
            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get("storedMessages.json")));
                JSONArray jsonArray = new JSONArray(content);
                for (int i = 0; i < jsonArray.length(); i++) {
                    stored.add(jsonArray.getJSONObject(i));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading stored messages: " + e.getMessage());
        }
        return stored;
    }

    public static void displaySenderRecipient() {
        ArrayList<JSONObject> stored = loadStoredMessages();
        if (stored.isEmpty()) {
            System.out.println("No stored messages found.");
        } else {
            System.out.println("Stored Message: ");
            for (int i = 0; i < stored.size(); i++) {
                if (stored.get(i).has("SenderNumber")) {
                    System.out.println("Sender: " + stored.get(i).getString("Sender"));
                } else {
                    System.out.println("Sender: " + sender);
                }
                System.out.println("Recipient: " + stored.get(i).getString("Recipient"));
            }
        }
    }

    public static String getLongestStoredMessage() {
        ArrayList<JSONObject> stored = loadStoredMessages();
        if (stored.isEmpty()) {
            return "No stored messages found.";
        }
        String longest = "";
        for (int i = 0; i < stored.size(); i++) {
            String text = stored.get(i).getString("Message");
            if (text.length() > longest.length()) {
                longest = text;
            }
        }
        return longest;
    }

    public static String searchByMessageID(String id) {
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).contains("Message ID: " + id)) {
                return sentMessages.get(i);
            }
        }
        ArrayList<JSONObject> stored = loadStoredMessages();
        for (int i = 0; i < stored.size(); i++) {
            if (stored.get(i).getString("MessageID").equals(id)) {
                return "Recipient: " + stored.get(i).getString("Recipient") + " | Message: " + stored.get(i).getString("Message");
            }
        }
        return "Message ID not found.";
    }

    public static void searchByRecipient(String recipientNumber) {
        boolean found = false;
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).contains("Recipient: " + recipientNumber)) {
                System.out.println(sentMessages.get(i));
                found = true;
            }
        }
        ArrayList<JSONObject> stored = loadStoredMessages();
        for (int i = 0; i < stored.size(); i++) {
            if (stored.get(i).getString("Recipient").equals(recipientNumber)) {
                System.out.println("Message: " + stored.get(i).getString("Message"));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found for recipient: " + recipientNumber);
        }
    }

    public static String deleteByHash(String hash) {
        try {
            ArrayList<JSONObject> stored = loadStoredMessages();
            ArrayList<JSONObject> updated = new ArrayList<JSONObject>();
            String deletedMessage = null;

            for (int i = 0; i < stored.size(); i++) {
                if (stored.get(i).getString("MessageHash").equals(hash)) {
                    deletedMessage = stored.get(i).getString("Message");
                } else {
                    updated.add(stored.get(i));
                }
            }

            if (deletedMessage == null) {
                return "Message hash not found.";
            }

            JSONArray newArray = new JSONArray();
            for (int i = 0; i < updated.size(); i++) {
                newArray.put(updated.get(i));
            }
            Files.write(Paths.get("storedMessages.json"), newArray.toString(2).getBytes());

            return "Message \"" + deletedMessage + "\" successfully deleted.";

        } catch (Exception e) {
            return "Error deleting message: " + e.getMessage();
        }
    }

    public static void displayReport() {
        ArrayList<JSONObject> stored = loadStoredMessages();
        if (stored.isEmpty()) {
            System.out.println("No messages to report.");
        } else {
            System.out.println("Full Message Report:");
            for (int i = 0; i < stored.size(); i++) {
                System.out.println("Message Hash: " + stored.get(i).getString("MessageHash"));
                System.out.println("Recipient: " + stored.get(i).getString("Recipient"));
                System.out.println("Message: " + stored.get(i).getString("Message"));
            }
        }
    }
}