import java.util.ArrayList;
import java.util.List;

public class Subject {
	private String subjectName;
	private String topStudent;
	private String dullestStudent;
	private ArrayList<Student> students;

	public Subject(String name) {
		this.subjectName = subjectName;
		this.students = new ArrayList<>();
		
	}

	public void addStudent(Student student){
		students.add(student);
	
	}
	
	public ArrayList<Student> getStudents() {
    	    return students;
	    }
	

	public String setTopStudent(String topStudentName){
		this.topStudent = topStudentName;
		return topStudentName;
	}

	public String setDullestStudent(String dullestStudentName){
		this.dullestStudent = dullestStudentName;
		return dullestStudentName;
	}


	public String getSubjectName() {
		return subjectName;
	}

	public String getTopStudent() {
		return topStudent;
    }
	

	public String getDullestStudent() {
	        return dullestStudent;
		}
}
