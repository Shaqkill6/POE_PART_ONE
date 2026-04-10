import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");

    //Login True and False
    @Test
    void testLoginSuccess() {
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    void testLoginFail() {
        assertFalse(login.loginUser("wrong", "wrong"));
    }

    //Login Status Message
    @Test
    void testLoginStatusMessages() {
        assertEquals("Successful login", login.returnLoginStatus(true));
        assertEquals("Failed login", login.returnLoginStatus(false));
    }
}