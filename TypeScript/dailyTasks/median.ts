

function sort(numbers: number[]) {
    for( let index: number = 0; index < numbers.length; index++) {
        
        for( let index2: number = 0; index2 < numbers.length - 1 - index; index2++) {
            if( numbers[index2] > numbers[index2 + 1] ) {
                let container: number = numbers[index2];
                numbers[index2] = numbers[index2 + 1];
                numbers[index2 + 1] = container;
            }
        }

    }
    return numbers;
}

function findMedian(numbers: number[]) {
    let sortedArray: number[] = sort(numbers);
    if( sortedArray.length % 2 !== 0 ) {
        let medianIndex: number = Math.floor((sortedArray.length/2));
        let median  = sortedArray[medianIndex];
        return median;
    }
    else{   
        let medianIndex1: number = Math.floor(sortedArray.length/2);
        let medianIndex2: number = Math.floor(sortedArray.length/2 - 1);
        let median = (sortedArray[medianIndex1] + sortedArray[medianIndex2]) / 2;
        return median;
    }


}
console.log(findMedian([20, 15, 21, 16, 17]));