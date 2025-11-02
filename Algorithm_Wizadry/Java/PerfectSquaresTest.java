import org.junit.jupiter.api.Test;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PerfectSquaresTest{
	
	PerfectSquare function;
	
	@BeforeEach
	void setup (){
	function = new PerfectSquare();
	}

	@Test
	public void testThatGetPerfectNumberCanDetermineIfASingleNumberIsAPerfectNumber(){
		List<Boolean> actual = function.getPerfectSquares(Arrays.asList(4));
		List<Boolean> expected = Arrays.asList(true);
		assertEquals(actual, expected); }
	@Test
	public void testThatGetPerfectSquaresReturnsCorrectListForMultipleNumbersInAList(){
		List<Boolean> actual = function.getPerfectSquares(Arrays.asList(4, 6, 25, 21, 100, 101));
		List<Boolean> expected = Arrays.asList(true, false, true, false, true, false);
		assertEquals(actual, expected);}

	@Test
	public void testThatGetPerfectSquareDoesntBreakWhenANonIntIsInTheList(){
		List<Boolean> actual = function.getPerfectSquares(Arrays.asList(4, "b", 25));
		List<Boolean> expected = Arrays.asList(true, false, true);
		assertEquals(actual, expected);
		}

	@Test
	public void testThatPerfectSquareReturnsCorrectOutputWithNegativeValues(){
		List<Boolean> actual = function.getPerfectSquares(Arrays.asList(4, 6, 'b', '$', -4 ));
		List<Boolean> expected = Arrays.asList(true, false, false, false, false);
	}
	
	

}