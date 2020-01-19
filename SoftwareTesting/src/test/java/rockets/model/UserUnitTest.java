package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTest {
    private User target;

    @BeforeEach
    public void setUp() {
        target = new User();
    }


    @DisplayName("should throw exception when pass a empty email address to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetEmailToEmpty(String email) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setEmail function")
    @Test
    public void shouldThrowExceptionWhenSetEmailToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setEmail(null));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exceptions when pass a null password to setPassword function")
    @Test
    public void shouldThrowExceptionWhenSetPasswordToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setPassword(null));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should return true when two users have the same email")
    @Test
    public void shouldReturnTrueWhenUsersHaveSameEmail() {
        String email = "abc@example.com";
        target.setEmail(email);
        User anotherUser = new User();
        anotherUser.setEmail(email);
        assertTrue(target.equals(anotherUser));
    }


    @DisplayName("should return false when two users have different emails")
    @Test
    public void shouldReturnFalseWhenUsersHaveDifferentEmails() {
        target.setEmail("abc@example.com");
        User anotherUser = new User();
        anotherUser.setEmail("def@example.com");
        assertFalse(target.equals(anotherUser));
    }

    @DisplayName("should return information of user")
    @Test
    public void shouldReturnUserInformationWhenToString() {

        target.setFirstName("Vicky");
        target.setLastName("Yu");
        target.setEmail("cyuu@monash");
        assertEquals("User{firstName='Vicky', lastName='Yu', email='cyuu@monash'}", target.toString());
    }


    @DisplayName("should Return False When Email Is Invalid")
    @Test
    public void shouldReturnFalseWhenEmailIsInvalid() {
        String email = "admin-monash.com";

        assertFalse(target.isValid(email));
    }

    @DisplayName("should Return True When Email Is valid")
    @Test
    public void shouldReturnTrueWhenEmailIsValid() {
        String email = "admin@monash.com";

        assertTrue(target.isValid(email));
    }



    @DisplayName("should throw exception when pass empty to setPassword function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetPasswordToEmpty(String password) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPassword(password));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }


    @DisplayName("should throw exceptions when pass a null lastName to setLastName function")
    @Test
    public void shouldThrowExceptionWhenSetLastNameToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setLastName(null));
        assertEquals("lastName cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass empty to setLastName function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetLastNameToEmpty(String lastName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setLastName(lastName));
        assertEquals("lastName cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should successfu msg when password length between 6 and 10 to checkpassword function")
    @ParameterizedTest
    @ValueSource(strings = {"123456", "aabbccdd", "aa11bb22cc"})
    public void shouldReturnSuccessMsgWhenChechPasswordLengthVaild(String password) {
        String loadedMsg = target.checkPassword(password);
        assertEquals("check password successful", loadedMsg);
    }

    @DisplayName("should fail msg when password length less than 6 to checkpassword function")
    @ParameterizedTest
    @ValueSource(strings = {"a", "1234", "2asx"})
    public void shouldReturnFailMsgWhenChechPasswordLengthLessThanSix(String password) {
        String loadedMsg = target.checkPassword(password);
        assertEquals("length of password should between 6 and 10", loadedMsg);
    }

    @DisplayName("should successfu msg when password length greatear than 10 to checkpassword function")
    @ParameterizedTest
    @ValueSource(strings = {"aabbccddeef", "112233445566", "aa11cc22dd445566"})
    public void shouldReturnFailMsgWhenChechPasswordLengthGreatThanTen(String password) {
        String loadedMsg = target.checkPassword(password);
        assertEquals("length of password should between 6 and 10", loadedMsg);
    }


}