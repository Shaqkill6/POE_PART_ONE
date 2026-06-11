import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Registration registration = new Registration();
        registration.Register();

        Login login = new Login(registration.userName, registration.password,
                registration.firstName, registration.lastName);
        login.loginProcess();

        Message.sender = registration.phoneNo;

        // Wipes the json file clean
        try {
            Files.write(Paths.get("storedMessages.json"), new JSONArray().toString(2).getBytes());
        } catch (Exception e) {
            System.out.println("Error resetting stored messages: " + e.getMessage());
        }

        System.out.println("Welcome to QuickChat.");

        int menuChoice = 0;

        while (menuChoice != 4) {

            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");
            System.out.print("Choose an option: ");
            menuChoice = Integer.parseInt(input.nextLine());

            if (menuChoice == 1) {

                System.out.print("How many messages do you want to send? ");
                int numMessages = Integer.parseInt(input.nextLine());

                for (int i = 1; i <= numMessages; i++) {

                    System.out.println("Message " + i);

                    System.out.print("Enter recipient number: ");
                    String recipient = input.nextLine();
                    Message tempMsg = new Message(recipient, "placeholder", i);
                    String recipientCheck = tempMsg.checkRecipientCell();
                    System.out.println(recipientCheck);

                    while (!recipientCheck.equals("Cell phone number successfully captured.")) {
                        System.out.print("Enter recipient number: ");
                        recipient = input.nextLine();
                        tempMsg = new Message(recipient, "placeholder", i);
                        recipientCheck = tempMsg.checkRecipientCell();
                        System.out.println(recipientCheck);
                    }

                    System.out.print("Enter message (max 250 chars): ");
                    String messageText = input.nextLine();

                    if (messageText.length() > 250) {
                        int excess = messageText.length() - 250;
                        System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                    } else {
                        System.out.println("Message ready to send.");

                        Message message = new Message(recipient, messageText, i);
                        System.out.println("Message ID generated: " + message.messageID);
                        System.out.println("Message Hash: " + message.messageHash);

                        System.out.println("0) Disregard Message");
                        System.out.println("1) Send Message");
                        System.out.println("2) Store Message to send later");
                        System.out.print("Choose: ");
                        int sendChoice = Integer.parseInt(input.nextLine());

                        System.out.println(message.SentMessage(sendChoice));
                        System.out.println("Message ID: " + message.messageID);
                        System.out.println("Message Hash: " + message.messageHash);
                        System.out.println("Recipient: " + message.recipient);
                        System.out.println("Message: " + message.messageText);
                    }
                }

                System.out.println("Total messages sent: " + Message.totalMessagesSent);
                Message.printMessages();

            } else if (menuChoice == 2) {
                Message.printMessages();

            } else if (menuChoice == 3) {

                System.out.println("Stored Messages");
                System.out.println("a) Display sender and recipient of all stored messages");
                System.out.println("b) Display longest stored message");
                System.out.println("c) Search for a message by ID");
                System.out.println("d) Search messages for a recipient");
                System.out.println("e) Delete a message by hash");
                System.out.println("f) Display full report");
                System.out.print("Choose an option: ");
                String subChoice = input.nextLine();

                if (subChoice.equals("a")) {
                    Message.displaySenderRecipient();
                } else if (subChoice.equals("b")) {
                    System.out.println("Longest stored message: " + Message.getLongestStoredMessage());
                } else if (subChoice.equals("c")) {
                    System.out.print("Enter Message ID to search: ");
                    System.out.println(Message.searchByMessageID(input.nextLine()));
                } else if (subChoice.equals("d")) {
                    System.out.print("Enter recipient number to search: ");
                    Message.searchByRecipient(input.nextLine());
                } else if (subChoice.equals("e")) {
                    System.out.print("Enter message hash to delete: ");
                    System.out.println(Message.deleteByHash(input.nextLine()));
                } else if (subChoice.equals("f")) {
                    Message.displayReport();
                } else {
                    System.out.println("Invalid option please select a letter.");
                }

            } else if (menuChoice == 4) {
                System.out.println("Goodbye!");
            }
            else {
                System.out.println("Invalid option, please select 1, 2, 3, or 4.");
            }
        }
    }
}