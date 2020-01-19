package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


public class LaunchServiceProviderUnitTest {
    private LaunchServiceProvider L;

    @BeforeEach
    public void setUp() {
        L = new LaunchServiceProvider("",  2019, "");
    }


    @DisplayName("should throw exception when pass empty to setName function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetNameToEmpty(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> L.setName(name));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }


    @DisplayName("should throw exception when pass null to setName function")
    @Test
    public void shouldThrowExceptionWhenSetNameToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> L.setName(null));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }




    @DisplayName("should throw exception when pass empty to setCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetCountryToEmpty(String country) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> L.setCountry(country));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setCountry function")
    @Test
    public void shouldThrowExceptionWhenSetCountryToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> L.setCountry(null));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should return true when two have the same name, country and yearFounded")
    @Test
    public void shouldReturnTureIfAllSame()
    {
        String name = "qwe";
        String country = "asd";
        int yearFounede = 2019;
        L.setName(name);
        L.setCountry(country);
        L.setYearFounded(yearFounede);
        LaunchServiceProvider anotherL = new LaunchServiceProvider("",123,"");
        anotherL.setName(name);
        anotherL.setCountry(country);
        anotherL.setYearFounded(yearFounede);
        assertTrue(L.equals(anotherL));
    }


    @DisplayName("should return false when two are different")
    @Test
    public void shouldReturnFalseIfDifferent()
    {
        L.setName("a");
        L.setCountry("z");
        L.setYearFounded(2018);
        LaunchServiceProvider anotherL = new LaunchServiceProvider("",123,"");
        anotherL.setName("b");
        anotherL.setCountry("x");
        anotherL.setYearFounded(2019);
        assertFalse(L.equals(anotherL));
    }


}
