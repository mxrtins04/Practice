package com.school.infrastructure.adapters;

import com.school.core.domain.Student;
import com.school.core.ports.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class LocalDatabaseAdapter implements StudentRepository {
    private final List<Student> students = new ArrayList<>();

    @Override
    public void save(Student student) {
        students.add(student);
        System.out.println("Student saved: " + student);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
}
