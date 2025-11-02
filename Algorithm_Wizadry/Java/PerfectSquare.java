import java.util.ArrayList;
import java.util.List;
public class PerfectSquare{

	public static List<Boolean> getPerfectSquares(List<?> numbers) {

		List<Boolean> output = new ArrayList<>();
		
		for (Object element : numbers) {
			if(!(element instanceof Integer)){
				output.add(false);
				continue;
		
			}
			int number = (Integer) element; 
				
			boolean isPerfect = false;
			int range = 1;
			int square = range * range;

			while( square <= number ){
				if (square == number){
				isPerfect = true;
				break;
			}
				else
					
			range++;
			square = range * range;
			}
			output.add(isPerfect);
			}
		return output;

}
}