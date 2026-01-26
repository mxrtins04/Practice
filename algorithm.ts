
const input: number[] = [1, 1, 2, 3, 3, 3, 4];

interface Output {
    unique: number[];
    [key: number]: number;
}
function findUnique(input1: number[]) {
    const unique: number[] = [];
    const result: Output = { unique: [] };

    for (const num of input1) {
        if (result[num]) result[num]++;

        else {
            result[num] = 1;
            unique.push(num);
        }
    }
    result.unique = unique;
    return result;
}

console.log(findUnique(input));

