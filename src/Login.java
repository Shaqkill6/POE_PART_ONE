import java.util.Scanner;

public class Login {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    Scanner input = new Scanner(System.in);

    public Login(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //Check login details
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(this.userName) && enteredPassword.equals(this.password);
    }

    //Return login status message
    public String returnLoginStatus(boolean status) {
        if (status) {
            return "Successful login";
        } else {
            return "Failed login";
        }
    }

    //Login loop
    public void loginProcess() {

        String registeredUsername;
        String registeredPassword;
        boolean status = false;

        System.out.println("Please log in.");

        while (!status) {

            System.out.print("Enter Username: ");
            registeredUsername = input.nextLine();

            System.out.print("Enter Password: ");
            registeredPassword = input.nextLine();

            status = loginUser(registeredUsername, registeredPassword);

            if (status) {
                System.out.println("Successful login");
                System.out.println("Welcome " + firstName + " " + lastName + ", it is great to see you again.");
            } else {
                System.out.println("Username or password incorrect, please try again.");
            }
        }
    }
}