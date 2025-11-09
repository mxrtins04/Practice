import java.util.ArrayList;
import java.util.Arrays;

public class StudentGradeFunctions{
	
/*	ArrayList<Integer> scoresOfStudents = new ArrayList<>();
	ArrayList<Integer> position = new ArrayList<>();
	ArrayList<Integer> totalScoresOfStudents = new ArrayList<>();
	ArrayList<Integer> averageScores = new ArrayList<>();
	*/

	







	ArrayList<Subject> subjects = new ArrayList<>();

	public int getNumberOfSubjectsOffered(ArrayList<Subject> subjects){
		numberOfSubjects = subjects.size();
		return numberOfSubjects;
	}

	public Integer validateScore(String score){
		try{
			return Integer.parseInt(score);
		}
		catch (NumberFormatException e) {
			//System.out.println("Please put in a number");
			return null;}	
	}


	

	public int [][] addStudent(int count, int [][] studentRecords){
		studentRecords[count][0] = count;
		return studentRecords;
	}

	public int [][] addScores(int count, int count2, int score, int [][] studentRecords){
		studentRecords[count][count2] = score;
		return studentRecords;
	}

	public String validateString(String name){
		for(char c : name.toCharArray()){
			if(!Character.isLetter(c))
			return(null);}
		return name;
	}

	public int getStudentTotal(int index, int [][]studentRecord, int total, ArrayList<Integer> totalScoresOfStudents){
		for( int score : studentRecord[index] ){
			total += score;}
		totalScoresOfStudents.add(total);
		return total;
		}
	
	public double calculateStudentAverage(int index, int [][]studentRecord, int total){
		int length = studentRecord[index].length;
		double average = (double) total / length;
		return average;
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