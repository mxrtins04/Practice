import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;


public class TurtleGraphicsTest{
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
		assertTrue(penDown);
	}

	@Test
	public void testThatTurtleDoesNotMoveOutOfTheFloor(){
		int row = 0;
		int column = 14;
		
	
		function.moveForward(14, row, column, penDown, floor);
		floor[0][14] = "xxx";

		String actual = floor[row][column];
		String expected = "xxx";
	
		assertEquals(actual, expected);
	}


	/*@Test
	public void testThatTurtleDrawsWhenPenIsDown(){
		function.graphicsFunction();
		int row = 0;
		int column = 0;
		function.moveForward(14, row, column, penDown);

		String actual = function.displayFloor(row, column);
		String expected = function.displayFloor(12, 0);
	
		assertEquals(actual, expected);

	}*/

	@Test
	public void testThatCurrentPositionChangesAfterMovement(){
		int row = 0;
		int column = 0;
		
		int[] newPosition = function.moveForward(10, row, column, penDown, floor);
		row = newPosition[0];
		column = newPosition[1];
		floor[0][10] = "intent";
		String actual = floor[row][column];
		String expected = "intent";
		
		assertEquals(actual, expected);

		
	}

	@Test
	public void testThatTurtleMarksInitialSpot(){
		int row = 0;
		int column = 0;
		int[] newPosition = function.moveForward(10, row, column, penDown, floor);
		row = newPosition[0];
		column = newPosition[1];
		
		String actual = floor[0][0];
		String expected = "*";
		
		assertEquals(actual, expected);


	}

	@Test
	public void testThatTheTurtleChangesDirection(){
		function.turnRight();
		assertEquals(2, function.direction);

		/*direction = 3;
		function.turnLeft();
		assertEquals(2, direction); */
		
	}
	
	
}