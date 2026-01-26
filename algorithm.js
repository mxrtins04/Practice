var input = [1, 1, 2, 3, 3, 3, 4];
function findUnique(input1) {
    var unique = [];
    var result = { unique: [] };
    for (var _i = 0, input1_1 = input1; _i < input1_1.length; _i++) {
        var num = input1_1[_i];
        if (result[num])
            result[num]++;
        else {
            result[num] = 1;
            unique.push(num);
        }
    }
    result.unique = unique;
    return result;
}
console.log(findUnique(input));
