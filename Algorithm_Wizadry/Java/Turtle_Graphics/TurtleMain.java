import java.util.Arrays;
public class TurtleMain {
    public static void main(String[] args) {
        TurtleGraphicsFunctions function = new TurtleGraphicsFunctions();
	String[][] floor = new String[2][10];
	function.graphicsFunction();
	int row = 0;
	int column = 0;
	int direction = 1;
        String[] commands = {
            "2", "5,4","6", "9"};

	boolean penDown = true;
	int index = 0;

	
	
	for ( String command : commands ) {
           
		if (command.equals("1")) {
			penDown = false;

		} else if (command.equals("2")) {
			penDown = true;

		}
		else if (command.equals("3")) {
			function.turnRight();
		} else if (command.equals("4")) {
			function.turnLeft();

		}
		else if (command.startsWith("5")) {
			String[] splice = command.split(",");
			String movement = splice[1];
			int steps = 0;
			steps = Integer.parseInt(movement);
		

			if (steps > 0){
				function.moveForward(steps, row, column, penDown);
				int[] currentPosition = function.moveForward(steps, row, column, penDown);
				row = currentPosition[0];
				column = currentPosition[1];}
		} 
	
		else if (command.equals("6")) {
			String[][] display = function.displayFloor(floor, row, column);
			System.out.print(Arrays.deepToString(display));
		} 
		else if (command.equals("9")) {
			break;
	}

            index++;
	}
}
}
