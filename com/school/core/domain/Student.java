package com.school.core.domain;

public class Student {
    private final String name;
    private final double gpa;
    private boolean admitted;

    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
        this.admitted = false;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public boolean isAdmitted() {
        return admitted;
    }

    public void setAdmitted(boolean admitted) {
        this.admitted = admitted;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gpa=" + gpa +
                ", admitted=" + admitted +
                '}';
    }
}
