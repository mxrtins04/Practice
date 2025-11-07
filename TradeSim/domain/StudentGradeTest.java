import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

public static StudentGradeTest{

	StudentGradeFunctions function;

	@BeforeEach
	void setup() {
		function = new StudentGradeFunctions();
    }

	@Test
	public void testThatValidateScoresTakesInOnlyInt(){
		int actual = function.validateScores('l');
		expected = "Please put in a number";
		selfAssert(actual, expected);
	}

}