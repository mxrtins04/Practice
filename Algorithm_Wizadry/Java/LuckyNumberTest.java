import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;

public class LuckyNumberTest{
	LuckyNumbers function;

	@BeforeEach
	void setup(){
		function = new LuckyNumbers();}
	

	@Test
	public void testThatGetLuckyNumberReturnsOnlyLuckyNumbers(){
		List<Integer> actual = function.getLuckyNumbers(Arrays.asList(14, 49, 20, 40, 70));
		List<Integer> expected = Arrays.asList(70);
		}
}