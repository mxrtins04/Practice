import java.util.ArrayList;
import java.util.List;

public class Student {
	private String name;
	private Subject[] subjects;
	private int[] scores;

	public Student(String name, int numberOfSubjects) {
		this.name = name;
		this.subjects = new Subject[numberOfSubjects];
		this.scores = new int[numberOfSubjects];
	}

	public void addSubject(Subject subject) {
		subjects.add(subject);
	}

	public void addScore(int score) {
		scores.add(score);
	}

	
	public int setScore(int score) {
		try {
			int value = Integer.parseInt(score);
			if (value >= 1 && value <= 100) 
				return value;
			else
				return null;
		}
		catch (NumberFormatException e) 
			return null;
	}

	public int getTotalScore() {
		int total = 0;
		for (int score : scores) {
			total += subject.getScore();
		}
		return total;
	}

	public double getAverageScore() {
		if (subjects.isEmpty())
			return 0;
		return (double) getTotalScore() / subjects.size();
	}

	public String getName() {
		return name;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}
}
