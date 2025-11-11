import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class StudentGrade{
public static void main(String [] args){

	Scanner input = new Scanner(System.in);

	ArrayList<Subject> subjects = new ArrayList<>();
	ArrayList<Student> students = new ArrayList<>();
	StudentGradeFunctions function = new StudentGradeFunctions();
	int numberOfSubjects = 0;
	
	
	while(true){
		System.out.print("What subjects are your students offering? ");
		String subjectName = input.nextLine().trim();
		if (subjectName.isEmpty()){
			System.out.println("Empty name not allowed.");
			continue; }
		Subject subject = new Subject(subjectName);
		subjects.add(subject);
		System.out.println("Would you like to add more subjects(Yes/No)? ");
		String response = input.nextLine().trim();
		if (response.equalsIgnoreCase("no")) 
			break;
		if( response.equalsIgnoreCase("yes"))
			continue;
		else
			System.out.println("please put in a yes or a no");
	}	

		numberOfSubjects = subjects.size();	
			while(true){
				System.out.print("Put in the names of your students: ");
				String studentName = input.nextLine().trim();

				if (studentName.isEmpty()) {
					System.out.println("Empty name not allowed.");
					continue;}
				
				Student student = new Student(studentName, numberOfSubjects);
				students.add(student);
				System.out.printf("%s added.", studentName);

				System.out.println("Would you like to add another student(Yes/No)? ");
				String response = input.nextLine().trim();
				if (response.equalsIgnoreCase("no")){
					System.out.print("Student names are: ");
					break;}
				if (!response.equalsIgnoreCase("yes")) {
					System.out.println("Type yes or no."); 						continue;
				}
				
			}
			
				for( Subject subjectCase : subjects ){
					int index = 0;
					for( Student studentCase : students ){
						while(true){
						System.out.printf("Input %s's score for %s: ", studentCase.getName(), subjectCase.getSubjectName());
						String rawScore = input.nextLine();
						int score = studentCase.setScore(rawScore);
						if( score == -1 ){
							System.out.println("Score must be in the range of 1 - 100");
							continue;}
						if( score == -2 ){
							System.out.println("Score must be a number");
						}
						studentCase.addScore(index, score);
						subjectCase.addStudent(studentCase);
	System.out.printf("%n%s added.", studentCase.getName());
						break;
					}}
					index++;
				}
				
				while(true){
					System.out.print("""
							All data have been collected and stored. What would you like to do now:
								1. Get class summary.
								2. Get subject summary.
								3. Exit.""");
					
					int response3 = input.nextInt();
					input.nextLine();
					
					switch(response3){
						case 1: 
							function.showClassSummary(students, subjects);
							
							break;
						case 2:
							break;
						case 3:
							break;
						default:
							break;
	}
	}}}




/*

		if( numberOfStudents == null || numberOfStudents > 100 || numberOfStudents < 0	){
			System.out.println("Please put in a valid number!!!");
			continue;}
		
		System.out.print("How many courses do they offer? ");
		String courses = input.next();
		input.nextLine();
		Integer numberOfCourses = function.validateScore(courses);
		if( numberOfCourses == null || numberOfStudents > 100 || numberOfStudents < 0){
			System.out.println("Please put in a number!!!");
			continue;}

		studentsRecord = new int[numberOfStudents][numberOfCourses];
		
		lengthOfRecord = studentsRecord.length;
		lengthOfScores = studentsRecord[0].length;
			for( int counter = 0; counter < lengthOfRecord; counter++ ){
			
			while(true){
			System.out.println("Input students name: ");
			String rawName = input.nextLine();

			studentName = function.validateString(rawName);
			
			if (studentName == null){
				System.out.println("Please input THE NAME of the student");
				continue;}
			else{
				System.out.printf("%s added to the database%n", studentName);
				function.addStudent(counter, studentsRecord);
				function.addName(studentName, names);
				System.out.printf("Input %s's score for each course: %n", studentName);
				break;}}
			
			

			
			for( int counter2 = 1; counter2 <= lengthOfScores; counter2++ ){
				
				int index = counter2 - 1;
				Integer studentScore = null;
				while(studentScore == null){
					System.out.print("Enter score for course " + counter2 + ": ");
					String rawScore = input.nextLine();
					studentScore = function.validateScore(rawScore);
				if (studentScore == null){
					System.out.println("Please input THE SCORE of the student");
					}
				else{
					function.addScores(counter, index, studentScore, studentsRecord);
					System.out.println("Score recorded. Input score for other courses: ");
					}}
				
			}
				System.out.printf("All scores for %s have been recorded.%n", studentName);
			}
		break;
		}		
		System.out.print("STUDENT	");
		for( int index = 0; index < lengthOfScores; index++ ){
			
			System.out.printf("\tSUB%d\t", index + 1);
		}
			System.out.print("\t\tTOTAL\tAVE\tPOS");
			

		 

		System.out.println();
		for( int index2 = 0; index2 < lengthOfRecord; index2++ ){
			function.getStudentTotal(index2, studentsRecord, 0, totalScoresOfStudents);
		}
		position = function.getPositionOfStudent( position, totalScoresOfStudents);

		for( int index2 = 0; index2 < lengthOfRecord; index2++) {
			int total = totalScoresOfStudents.get(index2);
			double average = function.calculateStudentAverage(index2, studentsRecord, total);
			int rank = position.get(index2);
			

			System.out.printf("%s\t",names.get(index2) );
			for( int score : studentsRecord[index2] ){
				System.out.printf("\t%d\t", score);}
		
		System.out.printf("\t\t%d\t%.2f\t%d%n", total, average, rank);

		
}

		System.out.println("\nCLASS SUMMARY");
		
		int highestPass = 0;
		int hardestSubject = 0;
		int highestTotal = totalScoresOfStudents.get(0);
		int lowestTotal = totalScoresOfStudents.get(0);
		String bestStudent = names.get(0);
		int subjectScoreTotal = 0;
		String worstStudent = names.get(0);
		int classTotal = 0;
		int highestScore = 0;
		int lowestScore = 100;
		double subjectAverage = 0.0;
		int pass = 0;
		int fail = 0;
		String highestStudent = names.get(0);
		String lowestStudent = names.get(0);
		int easiestSubject = 0;
		int highestFail = 0;

		for (int count = 0; count < totalScoresOfStudents.size(); count++) { 
			int total = totalScoresOfStudents.get(count);
			classTotal += total;

			if (total > highestTotal) {
			highestTotal = total;
			bestStudent = names.get(count);
			}

			if (total < lowestTotal) {
				lowestTotal = total;
				worstStudent = names.get(count);
			}
		}


		double classAverage = (double) classTotal / totalScoresOfStudents.size();
		System.out.println("===========================");
		System.out.printf("Best student: %s => Total: %d%n", bestStudent, highestTotal);
		System.out.printf("Worst student: %s => Total: %d%n", worstStudent, lowestTotal);
		

		System.out.printf("Class total score: %d%n", classTotal);
		System.out.printf("Class average score: %.2f%n", classAverage);
		System.out.println("===========================\n");


		System.out.println("\nSUBJECT SUMMARY");
		
		for( int subject = 0; subject < lengthOfScores; subject++ ){
			
			System.out.printf("%nSubject%d SUMMARY%n", subject + 1);
			System.out.println("===========================");
		
			
			for( int firstIndex = 0; firstIndex < lengthOfRecord; firstIndex++ ){
	
				int subjectScore = studentsRecord[firstIndex][subject];
				if (subjectScore >= 50)
					pass += 1;
				else
					fail +=1;

				subjectScoreTotal += subjectScore;
				subjectAverage = (double) subjectScoreTotal / firstIndex;

				if (subjectScore > highestScore){
					highestScore = subjectScore;
					highestStudent = names.get(firstIndex);}

				if( subjectScore < lowestScore ){
					lowestScore = subjectScore;
					lowestStudent = names.get(firstIndex);}

				if (fail > highestFail){
					highestFail = fail;
					hardestSubject = subject;}

				if (pass > highestPass){
					highestPass = pass;
					easiestSubject = subject;}
				
			}
 
		System.out.printf("Highest scoring student is: %s scoring %d%n", highestStudent, highestScore);
		System.out.printf("%nLowest scoring student is: %s scoring %d", lowestStudent, lowestScore);
		System.out.printf("%nTotal score is: %d", subjectScoreTotal);
		System.out.printf("%nAverage score is: %f", subjectAverage);
		System.out.printf("%nNumber of passes: %d", pass);
		System.out.printf("%nNumber of fails: %d", fail);
		lowestScore = 100;
		pass = 0;
		fail = 0;
		}
	
		System.out.printf("%nThe hardest subject is subject %d, with %d fails", hardestSubject + 1, highestFail);
		System.out.printf("%nThe easiest subject is subject %d, with %d passes", easiestSubject + 1, highestPass );*/

