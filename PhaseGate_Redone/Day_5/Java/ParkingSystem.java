import java.util.ArrayList;
import java.util.Scanner;

public class ParkingSystem {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ParkingSystemFunctions function = new ParkingSystemFunctions();
        int[] parkingSpace = new int[20];
        ArrayList<Integer> availableSpace = function.displayAvailableSpots(parkingSpace);

        String menu = """
                Would you like to:
                1. Reserve a spot.
                2. Leave.
                3. Exit.
                """;

        while (true) {
            System.out.print(menu);
            String rawOption = input.next();
            Integer option = function.verifyInputIsANumber(rawOption);
            if (option == null) 
		continue;
            option = function.verifyUsersInputIsWithinGivenRange(option);
            if (option == null) 
		continue;

            if (option == 1) {
                System.out.println("Available spots:");
                for (int spot : availableSpace) 
			System.out.println(spot);

                Integer chosen = null;
                while (chosen == null) {
                    System.out.print("Pick a spot: ");
                    String raw = input.next();
                    Integer number = function.verifyInputIsANumber(raw);
                    if (number == null) 
			continue;
                    number = function.verifyUsersInputIsWithinGivenRange(number);
                    if (number == null) 
			continue;
                    chosen = number;
                }

                ArrayList<Integer> updated = function.getASpot(availableSpace, chosen);
                if (!updated.equals(availableSpace)) {
                    availableSpace = updated;
                    parkingSpace[chosen - 1] = 1;
                    System.out.println("Spot " + chosen + " reserved.");
                }

            } else if (option == 2) {
                Integer spotLeft = null;
                while (spotLeft == null) {
                    System.out.print("Which spot are you leaving: ");
                    String raw = input.next();
                    Integer number = function.verifyInputIsANumber(raw);
                    if (number == null)
			continue;
                    number = function.verifyUsersInputIsWithinGivenRange(number);
                    if (number == null) 
			continue;
                    spotLeft = number;
                }

                if (parkingSpace[spotLeft - 1] == 0) {
                    System.out.println("That spot is already empty.");
                } else {
                    parkingSpace[spotLeft - 1] = 0;
                    availableSpace = function.leaveSpot(availableSpace, spotLeft);
                    System.out.println("Spot " + spotLeft + " freed.");
                }

            } else if (option == 3) {
                System.out.println("Goodbye.");
                break;
            } else {
                System.out.println("Enter a valid option.");
          }
     }

}
}
