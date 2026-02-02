package com.school.core.ports;

import com.school.core.domain.Student;

public interface AdmissionUseCase {
    boolean applyForAdmission(Student student);
}
