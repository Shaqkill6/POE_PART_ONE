import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    Registration reg = new Registration();

    //Username Test
    @Test
    void testUsernameCorrect() {
        assertTrue(reg.checkUserName("kyl_1"));
    }

    @Test
    void testUsernameIncorrect() {
        assertFalse(reg.checkUserName("kyle!!!!!!!"));
    }

    //Password Test
    @Test
    void testPasswordCorrect() {
        assertTrue(reg.checkPassword("Ch&&sec@ke99!"));
    }

    @Test
    void testPasswordIncorrect() {
        assertFalse(reg.checkPassword("password"));
    }

    //Cellphone Test
    @Test
    void testCellphoneCorrect() {
        assertTrue(reg.checkPhoneNo("+27838968976"));
    }

    @Test
    void testCellphoneIncorrect() {
        assertFalse(reg.checkPhoneNo("08966553"));
    }
}
