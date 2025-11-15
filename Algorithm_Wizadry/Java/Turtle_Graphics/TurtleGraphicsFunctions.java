public class TurtleGraphicsFunctions{
/*	public void determineDirection(String [][] currentPosition, String input){
		int row = 0;
		int colunm = 0;
		String[][] floor = new String[20][20];
		
		boolean penDown = false;
		int direction = 1;
*/

	String[][] floor = new String[20][20];
	
	boolean penDown = false;
	int direction = 1;


	public void graphicsFunction(){
		int arrayOne = 0;
		while (arrayOne < 20) {
			int arrayTwo = 0;
			while (arrayTwo < 20) {
				floor[arrayOne][arrayTwo] = "o";
				arrayTwo++;
			}
			arrayOne++;
		}
			
	}

	

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

	public int[] moveForward(int steps, int row, int column, boolean penDown, String[][] floor){
		if (steps <= 0)
		return null;
		if (penDown == true)
				floor[row][column] = "*";

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
		progress++;
		}
	
		return new int[]{row, column};
	}


	public void displayFloor(int row, int column) {
		row = 0;
		while (row < 20) {
			column = 0;
			while (column < 20) {
				System.out.print(floor[row][column]);
				column++;
			}
			System.out.println();
            row++;
		}
	}



}
	

		
			
			
		
		