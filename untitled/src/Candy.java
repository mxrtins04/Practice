class Solution {
    public int candy(int[] ratings) {
        int index = 0;
        int numberOfCandies = 1;
        int sumOfAllCandies = 0;
        for(int rating : ratings){
            if( ratingGreaterThanNeigbhour(index, ratings)) numberOfCandies++;
            else
            if( numberOfCandies > 1)
                numberOfCandies--;
            sumOfAllCandies += numberOfCandies;
            index++;

        }
        return sumOfAllCandies;
    }

    private boolean ratingGreaterThanNeigbhour(int index, int[] ratings){
        if( index == 0 ){
            if (checkFirstIndexRating(index, ratings))
                return true;
        }

        if( index == (ratings.length - 1)){
            if( checkLastIndexRating(index, ratings))
                return true;
        }
        if( index !=  0 || index != (ratings.length - 1)){
            if( ratings[index] > ratings[index + 1] && ratings[index] > ratings[index - 1])
                return true;
        }
        return false;
    }

    private boolean checkFirstIndexRating(int index, int[] ratings){
        if( ratings[index] > ratings[index + 1] )
            return true;
        return false;
    }

    private boolean checkLastIndexRating(int index, int[] ratings){
        if( ratings[index] > ratings[index - 1] )
            return true;
        return false;
    }
}