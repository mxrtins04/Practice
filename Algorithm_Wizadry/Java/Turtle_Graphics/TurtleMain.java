public class TurtleMain {
    public static void main(String[] args) {
        TurtleGraphicsFunctions function = new TurtleGraphicsFunctions();
	String[][] floor = new String[20][20];
	int row = 0;
	int column = 0;
	int direction = 1;
        String[] commands = {
            "2", "5,12", "3", "5,12", "3", "5,12", "3", "5,12", "1", "6", "9"};

	boolean penDown;
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
		

			if (steps > 0)
				function.moveForward(steps, row, columni);
		} 
	
		else if (command.equals("6")) {
			function.displayFloor(row, column);
		} 
		else if (command.equals("9")) {
			break;
	}

            index++;
	}
}
}
