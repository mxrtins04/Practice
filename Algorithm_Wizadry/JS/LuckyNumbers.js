class LuckyNumbers {

    getLuckyNumbers(numbers) {
        const luckyNumbers = [];
        let sum = 0;

        for (let item of numbers) {
            let number = item;

            while (number > 0) {
                let lastNumber = number % 10;
                sum += lastNumber;
                number = Math.floor(number / 10);
            }

            if ((sum % 7) === 0) {
                luckyNumbers.push(number);
            }
        }

        return luckyNumbers;
    }
}
