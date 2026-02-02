package com.school.core.ports;

import com.school.core.domain.Student;

public interface StudentRepository {
    void save(Student student);
}
