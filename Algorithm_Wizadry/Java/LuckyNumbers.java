import java.util.ArrayList;
import java.util.List;

public class LuckyNumbers{
	
	public List<Integer> getLuckyNumbers(List<?> numbers){ 
		List<Integer> luckyNumbers = new ArrayList<>();
		int sum = 0;
		for( Object item : numbers ){
		
		int number = (Integer) item;
		
		while( number > 0 ){
			int lastNumber = number % 10;
			sum += lastNumber;
			number /= 10;}

		if( ( sum % 7 ) == 0 )
			luckyNumbers.add(number);	
		

		
		
		}
		
		return(luckyNumbers);

	}

}