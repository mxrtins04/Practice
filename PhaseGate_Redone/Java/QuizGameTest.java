import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertTrue;


public class QuizGameTest{
	int score = 0;
	QuizGameFunctions function;

		@BeforeEach
		void setup (){

			function = new QuizGameFunctions();
		}
	@Test
	public void testThatGetQuestionGeneratesANumber(){
		int result = function.generateRandomNumber();
		
		assertTrue(result >= 1 && result <= 50);
		
	}

/*	@Test public void testThatNoQuestionIsRepeated(){
		int actual = function.generateRandomNumber();
		int expected = function.generateRandomNumber();

		assertEquals(actual, expected);
		}

*/

	@Test public void testThatReturnQuestionReturnsANumber(){
		String actual = function.returnQuestion(10);
		String expected = "Which of these can store multiple values of the same type?";
		assertEquals(actual, expected);

		String actual2 = function.returnQuestion(38);
		String expected2 = "Which collection class allows key-value pairs?";
		assertEquals(actual2, expected2);
	}

	@Test public void testThatCheckIfUserInputIsCorrectMarksWell(){
			String actual = function.checkIfUserInputIsCorrect(46, 'c');
			String expected = "Correct";
			assertEquals(actual, expected);

			String actual2 = function.checkIfUserInputIsCorrect(43, 'c');
			String expected2 = "Incorrect";
			assertEquals(actual2, expected2);
	}

	@Test public void testIncreaseScoreIncreasesScore(){
			int actual = function.increaseScore();
			int expected = 1;
			assertEquals(actual, expected);

			int actual2 = function.increaseScore();
			int expected2 = 2;
			assertEquals(actual2, expected2);


		}
	@Test public void testCountQuestionsAnsweredCountsQuestionsAnswered(){
			int actual = function.countQuestionsAnswered();
			int expected = 1;
			assertEquals(actual, expected);

			int actual2 = function.countQuestionsAnswered();
			int expected2 = 2;
			assertEquals(actual2, expected2);



	}

		}
/*	@Test public void testCountQuestionsAnsweredCountsQuestionsAnsweredFromFunction(){
			function.checkIfUserInputIsCorrect(46, 'c');
			assertEquals(1, functions.countQuestionAnswered);}*/
}