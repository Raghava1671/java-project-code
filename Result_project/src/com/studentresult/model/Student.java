package com.studentresult.model;

/**
 * Encapsulates student data and result calculation logic.
 */
public class Student {
    private int studentId;
    private String name;
    private int subject1Marks;
    private int subject2Marks;
    private int subject3Marks;
    private int total;
    private double percentage;
    private String grade;

    public Student() {
    }

    public Student(int studentId, String name, int subject1Marks, int subject2Marks, int subject3Marks) {
        this.studentId = studentId;
        this.name = name;
        this.subject1Marks = subject1Marks;
        this.subject2Marks = subject2Marks;
        this.subject3Marks = subject3Marks;
        calculateResults();
    }

    // Getters and Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSubject1Marks() { return subject1Marks; }
    public void setSubject1Marks(int subject1Marks) { this.subject1Marks = subject1Marks; calculateResults(); }

    public int getSubject2Marks() { return subject2Marks; }
    public void setSubject2Marks(int subject2Marks) { this.subject2Marks = subject2Marks; calculateResults(); }

    public int getSubject3Marks() { return subject3Marks; }
    public void setSubject3Marks(int subject3Marks) { this.subject3Marks = subject3Marks; calculateResults(); }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; } // Setter needed for loading from DB

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; } // Setter needed for loading from DB

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; } // Setter needed for loading from DB

    /**
     * Calculates total sum, percentage, and grade.
     */
    public void calculateResults() {
        this.total = this.subject1Marks + this.subject2Marks + this.subject3Marks;
        this.percentage = (double) this.total / 3.0;

        if (this.percentage >= 90) {
            this.grade = "A+";
        } else if (this.percentage >= 80) {
            this.grade = "A";
        } else if (this.percentage >= 70) {
            this.grade = "B";
        } else if (this.percentage >= 60) {
            this.grade = "C";
        } else if (this.percentage >= 50) {
            this.grade = "D";
        } else {
            this.grade = "F";
        }
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId +
                ", Name: '" + name + '\'' +
                ", Marks: [" + subject1Marks + ", " + subject2Marks + ", " + subject3Marks + "]" +
                ", Total: " + total +
                ", Percentage: " + String.format("%.2f", percentage) + "%" +
                ", Grade: " + grade;
    }
}
