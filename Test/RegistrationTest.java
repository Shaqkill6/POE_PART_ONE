import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    Registration reg = new Registration();

    //Username Test
    @Test
    public void testUsernameCorrect() {
        reg.checkUserName("kyl_1");
        String expected = "Welcome <user first name>,<user second name> it is great to see you.";
        assertEquals(expected, "Welcome <user first name>,<user second name> it is great to see you.");
    }

    @Test
    public void testUsernameIncorrect() {
        reg.checkUserName("kyle!!!!!!!");
        String expected = "Username incorrectly formatted, make sure it contains an underscore and is no more than 5 characters long.";
        assertEquals(expected,"Username incorrectly formatted, make sure it contains an underscore and is no more than 5 characters long.");
    }

    //Password Test
    @Test
    public void testPasswordCorrect() {
        reg.checkPassword("Ch&&sec@ke99!");
        String expected = "Password captured";
        assertEquals(expected,"Password captured");
    }

    @Test
    public void testPasswordIncorrect() {
        reg.checkPassword("password");
        String expected = "Password is not correctly formatted correctly,make sure contains a symbol and a capital letter";
        assertEquals(expected,"Password is not correctly formatted correctly,make sure contains a symbol and a capital letter");
    }

    //Cellphone Test
    @Test
    public void testCellphoneCorrect() {
        reg.checkPhoneNo("+27838968976");
        String expected = "Number captured successfully";
        assertEquals(expected,"Number captured successfully");
    }

    @Test
    public void testCellphoneIncorrect() {
        reg.checkPhoneNo("08966553");
        String expected = "Number not entered correctly or does not contain international code";
        assertEquals(expected,"Number not entered correctly or does not contain international code");
    }

    @Test
     public void testUsernameTrue() {
        assertTrue(reg.checkUserName("kyl_1"));
    }

    public @Test
    void testUsernameFalse() {
        assertFalse(reg.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordTrue() {
        assertTrue(reg.checkPassword("Ch&&sec@ke99!"+ "Password successfully captured"));
    }

    @Test
    public void testPasswordFalse() {
        assertFalse(reg.checkPassword("password"+ "Password is not correctly formatted;please ensure that the password contains at least eight characters,a capital letter,a number and a special character."));
    }
}
