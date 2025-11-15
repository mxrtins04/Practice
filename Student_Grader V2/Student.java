import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Student {
	private String name;
	private Subject[] subjects;
	private int position;

	public Student(String name, int numberOfSubjects) {
		this.name = name;
		this.subjects = new Subject[numberOfSubjects];
		this.scores = new int[numberOfSubjects];
	}

	public void addSubject(int index, Subject subject) {
		subjects[index] = subject;
	}

	public void addScore(int index, int score) {
		scores[index] = score;
	}

	
	public int setScore(String score) {
		try {
			int value = Integer.parseInt(score);
			if (value >= 1 && value <= 100) 
				return value;
			else
				return -1;
		}
		catch (NumberFormatException e){
			return -2;}
		}
	

	public int getTotalScore(int[] scores) {
		int total = 0;
		for (int score : scores) {
			total += score;
		}
		return total;
	}

	public double getAverageScore(int totalScore) {
		if (subjects.length == 0)
			return 0;
		return (double) totalScore / subjects.length;
	}

	public String getName() {
		return name;
	}

	public Subject[] getSubjects() {
		return subjects;
	}

	public String toString() {
		return name + " -> Scores: " + Arrays.toString(scores);
	}
}
