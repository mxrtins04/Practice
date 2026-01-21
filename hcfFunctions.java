import java.util.ArrayList;

public class hcfFunctions {
    public ArrayList<Integer> findHcf(int[] array) {
        int divisor = 2;
        int lowestNumber = getMinimumNumber(array);
        ArrayList<Integer> result = new ArrayList<>();
        boolean done = false;
        while(!done){
            boolean divided = true;
            if( divisor > lowestNumber) done = true;
            for (int i = 0; i < array.length; i++) {
                if (array[i] % divisor != 0) {
                    divided = false;
                    divisor++;
                    break;
                }
            }
            
            if (divided) {
                result.add(divisor);
                for (int i = 0; i < array.length; i++) 
                    array[i] /= divisor;
            }
    
        }
        return result;
    }

    public int getMinimumNumber(int[] array){
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min)  min = array[i];
    
        }
        return min;
    }
}
