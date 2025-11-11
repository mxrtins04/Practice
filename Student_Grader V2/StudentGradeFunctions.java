import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class StudentGradeFunctions{

	ArrayList<Subject> subjects = new ArrayList<>();
	ArrayList<Student> students = new ArrayList<>();

	
	public static void showClassSummary(ArrayList<Student> students, ArrayList<Subject> subjects) {
	if (students.isEmpty()) {
		System.out.println("No students yet.");
		return;
	}

	System.out.printf("%15s", "Student");
	for (Subject subject : subjects) {
		System.out.printf("%-12s", subject.getSubjectName());
	}
	System.out.printf("%10s%10s%n", "Total", "Average");

	for (int index = 0; index < students.size(); index++) {
		Student student = students.get(index);
		System.out.printf("%15s", student.getName());
		for (int index2 = 0; index2 < subjects.size(); index2++) {
			System.out.printf("%12.2f", student.getScores().get(index2));
		}
		System.out.printf("%10.2f%10.2f%n", s.getTotalScore(), s.getAverageScore());
	}
}


	public ArrayList<Integer> getPositionOfStudent( ArrayList<Integer> position, ArrayList<Integer> total){
		
		for( int number : total ){
			Integer rank = 0;
			int testCase = number;
			for( int number1 : total ){
				if (testCase <= number1)
				rank++;
			}
		position.add(rank);
		}
		return position;
	}
		
}