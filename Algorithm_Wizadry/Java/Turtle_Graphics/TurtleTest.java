import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

public class TurtleGraphics{
	TurtleGraphicsFunctions function;
	@BeforeEach
	void setup(){
		function = new TurtleGraphicsFunctions();
	}
	
	int direction = 1;
	boolean penDown = true;
	String[][] floor = new String[20][20];

	@Test
	public void testThatThePenIsDownByDefault(){
		boolean actual = System.out.print(penDown);
		boolean expected = true;
		assertEquals(actual, expected);
	}

	@Test
	public void testThatTurtleDoesNotMoveIfMovementTakesItOutOfTheFloor(){
		int row = 12;
		int column = 0;
		function.moveForward(14, row, column, penDown);

		int [] actual = function.displayFloor(row, column);
		int [] expected = function.displayFloor(12, 0);
	
		assertEquals(actual, expected);
	}


	@Test
	public void testThatTurtleDrawsWhenPenIsDown(){
		
	}
}