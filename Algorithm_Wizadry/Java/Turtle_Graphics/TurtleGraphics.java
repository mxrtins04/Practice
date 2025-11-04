public class TurtleGraphics{
/*	public void determineDirection(String [][] currentPosition, String input){
		int row = 0;
		int colunm = 0;
		String[][] floor = new String[20][20];
		
		boolean penDown = false;
		int direction = 1;
*/

	String[][] floor = new String[20][20];
	int row = 0;
	int column = 0;
	boolean penDown = false;
	int direction = 1;


	public void turnRight() {
		direction = direction + 1;
		if (direction > 4)
			direction = 1;
		}

	public void turnLeft() {
		direction = direction - 1;
		if (direction < 1)
			direction = 4;
		}

	public void moveForward(int steps){
		if (steps <= 0)
		return;
			
		int progress = 0;
		while (progress < steps){
			if( direction ==1 ) {
				if( column + 1 >= 20 )
			break;
		column = column + 1;
			}
		else if (direction == 2) {
			if (row + 1 >= 20)
				break;
			row = row + 1;
		}
		else if (direction == 3) {
			if (column - 1 < 0)
				break;
			column = column - 1;
            	} 
		else if (direction == 4) {
			if (row - 1 < 0) 
				break;
		row = row - 1;
           	}

		if (penDown == true)
			floor[row][column] = "*";
		progress++;
		}
	}


	public void displayFloor() {
		int firstArray = 0;
		while (firstArray < 20) {
			int secondArray = 0;
			while (secondArray < 20) {
				System.out.print(floor[firstArray][secondArray]);
				secondArray++;
			}	
			System.out.println();
            firstArray++;
		}
	}



}
	

		
			
			
		
		