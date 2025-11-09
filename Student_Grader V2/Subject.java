import java.util.ArrayList;
import java.util.List;

public class Subject {
	private String name;
	private static String topStudent;
	private static String dullestStudent;
	private ArrayList<Student> students;

	public Subject(String name) {
		this.name = name;
		
	}

	public ArrayList<Student> addName(Student name){
		students.add(name);
		return names;
	}

	public String setTopStudent(String topStudentName){
		this.topStudent = topStudentName;
	}

	public String setDullestStudent(String dullestStudentName){
		this.dullestStudent = dullestStudentName;
	}


	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	
}
