import java.util.ArrayList;

public class ParkingSystemFunctions {

    public Integer verifyInputIsANumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please put in a number");
            return null;
        }
    }

    public Integer verifyUsersInputIsWithinGivenRange(Integer value) {
        if (value == null) return null;
        if (value < 1 || value > 20) {
            System.out.println("Please put in number within given range");
            return null;
        }
        return value;
    }

    public ArrayList<Integer> displayAvailableSpots(int[] parkingSpace) {
        ArrayList<Integer> availableSpace = new ArrayList<>();
        for (int i = 0; i < parkingSpace.length; i++) {
            if (parkingSpace[i] == 0) availableSpace.add(i + 1);
        }
        return availableSpace;
    }

    public ArrayList<Integer> getASpot(ArrayList<Integer> availableSpace, int userInput) {
        ArrayList<Integer> updated = new ArrayList<>(availableSpace);
        if (availableSpace.contains(userInput)) {
            updated.remove(Integer.valueOf(userInput));
        } else {
            System.out.println("Invalid or unavailable spot");
        }
        return updated;
    }

    public ArrayList<Integer> leaveSpot(ArrayList<Integer> availableSpace, int userInput) {
        ArrayList<Integer> updated = new ArrayList<>(availableSpace);
        if (!availableSpace.contains(userInput)) {
            updated.add(userInput);
        } else {
            System.out.println("Spot is already free");
        }
        return updated;
    }
}
