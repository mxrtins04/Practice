import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class ParkingSystemFunctionsTest{
	int[] parkingSpots = {0, 0, 0, 0, 0};
	ParkingSystemFunctions function;

	@BeforeEach
	void setup(){
		function = new ParkingSystemFunctions();
		}
	
	@Test
	public void testThatDisplayAvailableSpotsShowsAllSpotsAreEmpty(){
		int[] actual = function.displayAvailableSpots(parkingSpots);
		int[] expected = {0, 1, 2, 3, 4};
		assertEquals(actual, expected);
		}


}


