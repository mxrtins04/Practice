import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ParkingSystemFunctionsTest {
    int[] parkingSpace = {0, 0, 0, 0, 0};
    ArrayList<Integer> availableSpot = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    ParkingSystemFunctions function;

    @BeforeEach
    void setup() {
        function = new ParkingSystemFunctions();
    }

    @Test
    public void testVerifyInputIsANumberRejectsLetters() {
        assertNull(function.verifyInputIsANumber("b"));
    }

    @Test
    public void testVerifyInputIsANumberAcceptsDigits() {
        Integer result = function.verifyInputIsANumber("5");
        assertEquals(5, result);
    }

    @Test
    public void testVerifyUsersInputIsWithinRangeRejectsTooHigh() {
        Integer result = function.verifyUsersInputIsWithinGivenRange(25);
        assertNull(result);
    }

    @Test
    public void testDisplayAvailableSpotsShowsAllEmpty() {
        ArrayList<Integer> actual = function.displayAvailableSpots(parkingSpace);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(expected, actual);
    }

    @Test
    public void testGetASpotRemovesChosenSpot() {
        ArrayList<Integer> actual = function.getASpot(availableSpot, 3);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 4, 5));
        assertEquals(expected, actual);
    }

    @Test
    public void testLeaveSpotAddsBackFreedSpot() {
        ArrayList<Integer> modified = new ArrayList<>(Arrays.asList(1, 3, 4, 5));
        ArrayList<Integer> actual = function.leaveSpot(modified, 2);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 3, 4, 5, 2));
        assertTrue(actual.containsAll(expected));
    }
}
