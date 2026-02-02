package com.school.core.services;

import com.school.core.domain.Student;
import com.school.core.ports.AdmissionUseCase;
import com.school.core.ports.StudentRepository;

public class SchoolAdmissionService implements AdmissionUseCase {
    private final StudentRepository studentRepository;

    public SchoolAdmissionService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean applyForAdmission(Student student) {
        if (student.getGpa() > 3.5) {
            student.setAdmitted(true);
            studentRepository.save(student);
            return true;
        } else {
            System.out.println("Denied");
            return false;
        }
    }
}
