function sort(numbers) {
    for (var index = 0; index < numbers.length; index++) {
        for (var index2 = 0; index2 < numbers.length - 1 - index; index2++) {
            if (numbers[index2] > numbers[index2 + 1]) {
                var container = numbers[index2];
                numbers[index2] = numbers[index2 + 1];
                numbers[index2 + 1] = container;
            }
        }
    }
    return numbers;
}
function findMedian(numbers) {
    var sortedArray = sort(numbers);
    if (sortedArray.length % 2 !== 0) {
        var medianIndex = Math.floor((sortedArray.length / 2));
        var median = sortedArray[medianIndex];
        return median;
    }
    else {
        var medianIndex1 = Math.floor(sortedArray.length / 2);
        var medianIndex2 = Math.floor(sortedArray.length / 2 - 1);
        var median = (sortedArray[medianIndex1] + sortedArray[medianIndex2]) / 2;
        return median;
    }
}
console.log(findMedian([20, 15, 21, 16, 17]));
