import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class StudentGradeTest {
	int[][] testArray = {
    {1, 2},
    {3, 4},
    {5, 6}
};

	ArrayList<Integer> scoresOfStudents = new ArrayList<>(Arrays.asList(137, 216, 154, 227));
	ArrayList<Integer> position = new ArrayList<>();
	ArrayList<Integer> totalScoresOfStudents = new ArrayList<>();


	ArrayList<String> names = new ArrayList<>();
	StudentGradeFunctions function;

	@BeforeEach
	void setup() {
		function = new StudentGradeFunctions();
    }

	@Test
	public void testThatValidateScoresTakesInOnlyNumbers(){
		assertNull( function.validateScore("l"));
	
		int actual = function.validateScore("3");
		int expected = 3;

		assertEquals(actual, expected);
	}

	/*@Test
	public void testThatValidateScoresTakesOnlyNumbersInRangeOneToHundred(){
		int actual = function.validateScore("-3")}*/

	@Test
	public void testThatAddNameCollectsOnlyStrings(){
		ArrayList<String> actual = function.addName("Martins", names);
		ArrayList<String> expected = new ArrayList<>(Arrays.asList("Martins"));
		 assertEquals(expected, actual);

	
	}

	@Test
	public void testThatValidateStringCollectsOnlyStringsWithAlphabets(){
		assertNull( function.validateString("2"));	
	}
	@Test
	public void testThatGetStudentTotalReturnsTotalOfNumbers(){
		
		int total = 0;
		int actual = function.getStudentTotal(0, testArray, total, totalScoresOfStudents);
		int expected = 3;
		assertEquals(actual, expected);
		
	}


	@Test
	public void testThatCalculateStudentAverageGivesCorrectOutput(){
		double actual = function.calculateStudentAverage(1, testArray, 5);
		double expected = 2.5;
		assertEquals(actual, expected);

	}

	@Test
	public void testThatGetPositionOfStudentReturnsProperPositions(){
		ArrayList<Integer> actual = function.getPositionOfStudent( position, scoresOfStudents);
		ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(4, 2, 3, 1));
		assertEquals(actual, expected);

	}

	

	

}