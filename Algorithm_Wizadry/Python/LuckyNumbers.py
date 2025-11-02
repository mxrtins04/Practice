class LuckyNumbers:
    
    def getLuckyNumbers(self, numbers):
        luckyNumbers = []
        sum = 0

        for item in numbers:
            number = int(item)

            while number > 0:
                lastNumber = number % 10
                sum += lastNumber
                number //= 10

            if (sum % 7) == 0:
                luckyNumbers.append(number)

        return luckyNumbers
