package rockets.model;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

public class RocketUnitTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName("should create rocket successfully when given right parameters to constructor")
    @Test
    public void shouldConstructRocketObject() {
        String name = "BFR";
        String country = "USA";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name, country, manufacturer);
        assertNotNull(bfr);
    }

    @DisplayName("should throw exception when given null name to constructor")
    @Test
    public void shouldThrowExceptionWhenNoNameGiven() {
        String country = "USA";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");

        assertThrows(NullPointerException.class, () -> new Rocket(null, country, manufacturer));
    }

    @DisplayName("should throw exception when given null country to constructor")
    @Test
    public void shouldThrowExceptionWhenNoCountryGiven() {
        String name = "BFR";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");

        assertThrows(NullPointerException.class, () -> new Rocket(name, null, manufacturer));
    }

    @DisplayName("should throw exception when given null manufacturer to constructor")
    @Test
    public void shouldThrowExceptionWhenNoManufacturerGiven() {
        String name = "BFR";
        String country = "USA";
        assertThrows(NullPointerException.class, () -> new Rocket(name, country, null));
    }

    @DisplayName("should set rocket massToLEO value")
    @ValueSource(strings = {"10000", "15000"})
    public void shouldSetMassToLEOWhenGivenCorrectValue(String massToLEO) {
        String name = "BFR";
        String country = "USA";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");

        Rocket bfr = new Rocket(name, country, manufacturer);

        bfr.setMassToLEO(massToLEO);
        assertEquals(massToLEO, bfr.getMassToLEO());
    }

    @DisplayName("should throw exception when set massToLEO to null")
    @Test
    public void shouldThrowExceptionWhenSetMassToLEOToNull() {
        String name = "BFR";
        String country = "USA";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name, country, manufacturer);
        assertThrows(NullPointerException.class, () -> bfr.setMassToLEO(null));
    }



    @DisplayName("should throw exception when pass a empty to setName function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetNameToEmpty(String name) {
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket target = new Rocket("BFR", "USA", manufacturer);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setName(name));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setName function")
    @Test
    public void shouldThrowExceptionWhenSetNameToNull() {
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket target = new Rocket("BFR", "USA", manufacturer);
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setName(null));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty country to setCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhensetCountryToEmpty(String country) {
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket target = new Rocket("BFR", "USA", manufacturer);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setCountry(country));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setCountry function")
    @Test
    public void shouldThrowExceptionWhenSetCountryToNull() {
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket target = new Rocket("BFR", "USA", manufacturer);
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setCountry(null));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

}