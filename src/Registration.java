import java.util.Scanner;

public class Registration {

    public String userName;
    public String password;
    public String firstName;
    public String lastName;
    public String phoneNo;

    Scanner input = new Scanner(System.in);

    // Username check
    public boolean checkUserName(String userName) {
        return userName.length() <= 5 && userName.contains("_");
    }

    // Password check
    public boolean checkPassword(String password) {
        if (password.length() < 8) return false;

        boolean hasUppercase = false;
        boolean hasSpecial = false;
        boolean hasDigit = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUppercase = true;
            if (!Character.isLetterOrDigit(ch)) hasSpecial = true;
            if (Character.isDigit(ch)) hasDigit = true;
        }

        return hasUppercase && hasSpecial && hasDigit;
    }

    // Phone number check
    public boolean checkPhoneNo(String phoneNo) {
        return phoneNo.matches("(\\+27|0)[0-9]{9}");
    }

    // Register method
    public void Register() {

        System.out.print("Enter First Name: ");
        firstName = input.nextLine();

        System.out.print("Enter Last Name: ");
        lastName = input.nextLine();

        // Username loop
        do {
            System.out.print("Enter Username ( username must contain '_' and be no more than 5 characters): ");
            userName = input.nextLine();

            if (!checkUserName(userName)) {
                System.out.println("Username is not correctly formatted,please ensure that your username contains an underscore and is no more than five characters in length.");
            }

        } while (!checkUserName(userName));
        System.out.println("Username successfully captured.");

        // Password loop
        do {
            System.out.print("Enter Password ( password must be at least 8 chars,  contain a capital, number, special char): ");
            password = input.nextLine();

            if (!checkPassword(password)) {
                System.out.println("Password is not correctly formatted,please ensure that the password contains at least eight characters, a capital letter,a number and a special character");
            }

        } while (!checkPassword(password));
        System.out.println("Password successfully captured.");

        // Phone loop
        do {
            System.out.print("Enter Phone Number (+27 or 0): ");
            phoneNo = input.nextLine();

            if (!checkPhoneNo(phoneNo)) {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            }

        } while (!checkPhoneNo(phoneNo));
        System.out.println("Cell phone number successfully added.");

        System.out.println("Registration successful\n");
    }
}
