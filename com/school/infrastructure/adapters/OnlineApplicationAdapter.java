package com.school.infrastructure.adapters;

import com.school.core.domain.Student;
import com.school.core.ports.AdmissionUseCase;

public class OnlineApplicationAdapter {
    private final AdmissionUseCase admissionUseCase;

    public OnlineApplicationAdapter(AdmissionUseCase admissionUseCase) {
        this.admissionUseCase = admissionUseCase;
    }

    public boolean submitApplication(String name, double gpa) {
        Student student = new Student(name, gpa);
        return admissionUseCase.applyForAdmission(student);
    }
}
