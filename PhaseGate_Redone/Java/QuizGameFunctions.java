import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import static java.util.Map.entry;
import java.util.Map;

public class QuizGameFunctions{
	
	int score = 0;
	int numberOfQuestionsAnswered = 0;
	Map<Integer, String> questions = Map.ofEntries(
		entry(1, "Which of these is not a Java keyword?"),
		entry(2, "Which data type is used to create a variable that should store text?"),
		entry(3, "How do you create a variable with the numeric value 5?"),
		entry(4, "Which method is used to output text to the console in Java?"),
		entry(5, "Which symbol is used to start a single-line comment?"),
		entry(6, "Which of these is a valid boolean literal in Java?"),
		entry(7, "Which keyword is used to define a class in Java?"),
		entry(8, "Which operator is used for equality comparison?"),
		entry(9, "What is the default value of an int variable?"),
		entry(10, "Which of these can store multiple values of the same type?"),
		entry(11, "What is the size of an int in Java?"),
		entry(12, "What is the entry point of a Java program?"),
		entry(13, "Which of these defines a constructor?"),
		entry(14, "Which access modifier makes a member visible only within the same class?"),
		entry(15, "What does JVM stand for?"),
		entry(16, "Which keyword is used to inherit a class?"),
		entry(17, "Which interface must be implemented to create a thread?"),
		entry(18, "Which of these is not a primitive data type?"),
		entry(19, "What is used to handle exceptions in Java?"),
		entry(20, "What will 5 / 2 output as an integer division?"),
		entry(21, "Which package is automatically imported in every Java program?"),
		entry(22, "What does this keyword refer to?"),
		entry(23, "What is the parent class of all classes in Java?"),
		entry(24, "Which keyword prevents inheritance?"),
		entry(25, "Which collection doesn’t allow duplicates?"),
		entry(26, "What does super keyword do?"),
		entry(27, "Which exception is thrown when dividing by zero?"),
		entry(28, "How do you declare a constant in Java?"),
		entry(29, "What is method overloading?"),
		entry(30, "What is method overriding?"),
		entry(31, "What keyword is used to stop a loop?"),
		entry(32, "Which loop runs at least once?"),
		entry(33, "What is the output of System.out.println(2 + 3 + \"Hello\")?"),
		entry(34, "Which file extension does compiled Java code have?"),
		entry(35, "Which keyword is used to import a package?"),
		entry(36, "Which of these is not a valid loop?"),
		entry(37, "What does System.exit(0) do?"),
		entry(38, "Which collection class allows key-value pairs?"),
		entry(39, "Which keyword is used to define an interface?"),
		entry(40, "What is the default value of a boolean?"),
		entry(41, "Which statement is used to handle multiple conditions?"),
		entry(42, "Which exception must be caught or declared?"),
		entry(43, "What does garbage collection do?"),
		entry(44, "Which class is used to take user input?"),
		entry(45, "What is the output type of System.currentTimeMillis()?"),
		entry(46, "What is null in Java?"),
		entry(47, "Which loop is best for iterating an array?"),
		entry(48, "Which operator is used for logical AND?"),
		entry(49, "Which method compares string values?"),
		entry(50, "What does OOP stand for?")

	);	

	Map<Integer, Character> answers = Map.ofEntries(
		entry(1, 'b'),
		entry(2, 'a'),
		entry(3, 'b'),
		entry(4, 'd'),
		entry(5, 'b'),
		entry(6, 'b'),
		entry(7, 'a'),
		entry(8, 'b'),
		entry(9, 'a'),
		entry(10, 'b'),
		entry(11, 'a'),
		entry(12, 'a'),
		entry(13, 'a'),
		entry(14, 'b'),
		entry(15, 'b'),
		entry(16, 'c'),
		entry(17, 'a'),
		entry(18, 'd'),
		entry(19, 'a'),
		entry(20, 'b'),
		entry(21, 'b'),
		entry(22, 'b'),
		entry(23, 'a'),
		entry(24, 'b'),
		entry(25, 'b'),
		entry(26, 'b'),
		entry(27, 'b'),
		entry(28, 'b'),
		entry(29, 'a'),
		entry(30, 'a'),
		entry(31, 'd'),
		entry(32, 'c'),
		entry(33, 'a'),
		entry(34, 'b'),
		entry(35, 'b'),
		entry(36, 'c'),
		entry(37, 'b'),
		entry(38, 'c'),
		entry(39, 'a'),
		entry(40, 'b'),
		entry(41, 'b'),
		entry(42, 'b'),
		entry(43, 'b'),
		entry(44, 'a'),
		entry(45, 'b'),
		entry(46, 'c'),
		entry(47, 'b'),
		entry(48, 'b'),
		entry(49, 'c'),
		entry(50, 'a')
	);


	ArrayList<Integer> attemptedQuestions = new ArrayList<Integer>();

	public int generateRandomNumber(){
		int questionNumber = ThreadLocalRandom.current().nextInt(1, 50 + 1);;
		if( attemptedQuestions.contains(questionNumber) ){
			generateRandomNumber();
			}
		else{
			attemptedQuestions.add(questionNumber);
			}
		return (questionNumber);
	}
		
	public String returnQuestion(int questionNumber){
		String question = questions.get(questionNumber);
		return(question);
	
	}

	public 

	public String checkIfUserInputIsCorrect(int questionNumber, char userInput){
		if( answers.get(questionNumber) == userInput ){
			increaseScore();
			countQuestionsAnswered();
			return("Correct");}
				
		else{
			countQuestionsAnswered();
			return("Incorrect");}
			}

	public String checkIfUserInputIsACharacter(char Input
		
	public int increaseScore(){
		score++;
		return (score);}

	public int countQuestionsAnswered(){
		numberOfQuestionsAnswered++;
		return (numberOfQuestionsAnswered);
		}
		

}