import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
org.junit.jupiter.api.Assertions.assertEquals;


public class PerfectSquaresTest{
	
	PerfectSquares function;
	
	@BeforeEach
	void setup (){
	function = new PerfectSquars();
	}

	@Test
	public void testThatGetPerfectNumberCanDetermineIfASingleNumberIsAPerfectNumber{
		Boolean actual = function.getPerfectSquares(Arrays.asList(4);
		Boolean expected = true;
		assertEquals(actual, expected); }
	
	

}