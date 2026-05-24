import java.util.Scanner;

    void main(){
        Scanner input = new Scanner(System.in);

        Registration registration = new Registration();
        registration.Register();

        Login login = new Login(registration.userName, registration.password,
                registration.firstName, registration.lastName);
        login.loginProcess();

        System.out.println("Welcome to QuickChat.");

        int menuChoice = 0;

        while (menuChoice != 3) {

            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
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
                    System.out.println(tempMsg.checkRecipientCell());
                    while (!tempMsg.checkRecipientCell().equals("Cell phone number successfully captured.")){
                        System.out.print("Enter recipient number: ");
                        recipient = input.nextLine();
                        tempMsg = new Message(recipient,"placeholder", i);
                        recipient = tempMsg.checkRecipientCell();
                        System.out.println(tempMsg.checkRecipientCell());
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

                        System.out.println("1) Send Message");
                        System.out.println("0) Disregard Message");
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
                System.out.println(new Message("", "", 0).printMessages());

            } else if (menuChoice == 2) {
                System.out.println("Coming Soon.");

            } else if (menuChoice == 3) {
                System.out.println("Goodbye!");
            }
        }
    }