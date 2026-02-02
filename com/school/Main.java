package com.school;

import com.school.core.services.SchoolAdmissionService;
import com.school.infrastructure.adapters.LocalDatabaseAdapter;
import com.school.infrastructure.adapters.OnlineApplicationAdapter;

public class Main {
    public static void main(String[] args) {
        LocalDatabaseAdapter database = new LocalDatabaseAdapter();

        SchoolAdmissionService admissionService = new SchoolAdmissionService(database);

        OnlineApplicationAdapter webForm = new OnlineApplicationAdapter(admissionService);

        System.out.println("=== Testing School Admission System ===");

        System.out.println("\nTesting student with GPA 3.8:");
        webForm.submitApplication("Alice Johnson", 3.8);

        System.out.println("\nTesting student with GPA 3.2:");
        webForm.submitApplication("Bob Smith", 3.2);

        System.out.println("\nTesting student with GPA 3.5:");
        webForm.submitApplication("Charlie Brown", 3.5);

        System.out.println("\nTesting student with GPA 4.0:");
        webForm.submitApplication("Diana Prince", 4.0);

        System.out.println("\n=== All Admitted Students ===");
        database.getAllStudents().forEach(System.out::println);
    }
}
