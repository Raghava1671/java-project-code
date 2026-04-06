package com.studentresult.main;

import com.studentresult.dao.StudentDAO;
import com.studentresult.model.Student;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Online Student Result System ===");
            System.out.println("1. Add New Student Record");
            System.out.println("2. Display Individual Student Result");
            System.out.println("3. Display All Student Results");
            System.out.println("4. Update Student Details and Marks");
            System.out.println("5. Delete Student Record");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayStudentResult();
                    break;
                case 3:
                    displayAllStudents();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select an option from 1 to 6.");
            }
        }
        scanner.close();
    }

    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            // Check if student exists
            if (studentDAO.getStudentById(id) != null) {
                System.out.println("Error: Student with ID " + id + " already exists.");
                return;
            }

            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();

            int m1 = getMarksInput("Enter Subject 1 Marks (0-100): ");
            int m2 = getMarksInput("Enter Subject 2 Marks (0-100): ");
            int m3 = getMarksInput("Enter Subject 3 Marks (0-100): ");

            Student student = new Student(id, name, m1, m2, m3);
            studentDAO.addStudent(student);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format for ID.");
        }
    }

    private static void displayStudentResult() {
        System.out.println("\n--- Display Student Result ---");
        System.out.print("Enter Student ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Student student = studentDAO.getStudentById(id);

            if (student != null) {
                System.out.println(
                        "-----------------------------------------------------------------------------------------");
                System.out.printf("%-10s | %-20s | %-15s | %-10s | %-12s | %-5s%n",
                        "ID", "Name", "Marks(1,2,3)", "Total", "Percentage", "Grade");
                System.out.println(
                        "-----------------------------------------------------------------------------------------");
                System.out.printf("%-10d | %-20s | %-15s | %-10d | %-12.2f%%| %-5s%n",
                        student.getStudentId(),
                        student.getName(),
                        student.getSubject1Marks() + "," + student.getSubject2Marks() + ","
                                + student.getSubject3Marks(),
                        student.getTotal(),
                        student.getPercentage(),
                        student.getGrade());
                System.out.println(
                        "-----------------------------------------------------------------------------------------");

            } else {
                System.out.println("Student with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format for ID.");
        }
    }

    private static void displayAllStudents() {
        System.out.println("\n--- All Student Results ---");
        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No student records found.");
        } else {
            System.out.println(
                    "-----------------------------------------------------------------------------------------");
            System.out.printf("%-10s | %-20s | %-15s | %-10s | %-12s | %-5s%n",
                    "ID", "Name", "Marks(1,2,3)", "Total", "Percentage", "Grade");
            System.out.println(
                    "-----------------------------------------------------------------------------------------");
            for (Student student : students) {
                System.out.printf("%-10d | %-20s | %-15s | %-10d | %-12.2f%%| %-5s%n",
                        student.getStudentId(),
                        student.getName(),
                        student.getSubject1Marks() + "," + student.getSubject2Marks() + ","
                                + student.getSubject3Marks(),
                        student.getTotal(),
                        student.getPercentage(),
                        student.getGrade());
            }
            System.out.println(
                    "-----------------------------------------------------------------------------------------");
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student Details ---");
        System.out.print("Enter Student ID to update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Student student = studentDAO.getStudentById(id);

            if (student == null) {
                System.out.println("Student with ID " + id + " not found.");
                return;
            }

            System.out.println("Current Name: " + student.getName());
            System.out.print("Enter New Name (or press Enter to keep current): ");
            String name = scanner.nextLine();
            if (!name.trim().isEmpty()) {
                student.setName(name);
            }

            int m1 = getOptionalMarksInput("Current Subject 1 Marks: " + student.getSubject1Marks()
                    + ". Enter New Marks (or -1 to keep current): ");
            if (m1 != -1)
                student.setSubject1Marks(m1);

            int m2 = getOptionalMarksInput("Current Subject 2 Marks: " + student.getSubject2Marks()
                    + ". Enter New Marks (or -1 to keep current): ");
            if (m2 != -1)
                student.setSubject2Marks(m2);

            int m3 = getOptionalMarksInput("Current Subject 3 Marks: " + student.getSubject3Marks()
                    + ". Enter New Marks (or -1 to keep current): ");
            if (m3 != -1)
                student.setSubject3Marks(m3);

            studentDAO.updateStudent(student);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input format for ID.");
        }
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student Record ---");
        System.out.print("Enter Student ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Are you sure you want to delete student with ID " + id + "? (y/n): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("y")) {
                studentDAO.deleteStudent(id);
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format for ID.");
        }
    }

    // Helper method to get strictly valid marks
    private static int getMarksInput(String prompt) {
        int marks = -1;
        while (marks < 0 || marks > 100) {
            System.out.print(prompt);
            try {
                marks = Integer.parseInt(scanner.nextLine());
                if (marks < 0 || marks > 100) {
                    System.out.println("Marks must be between 0 and 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
        return marks;
    }

    // Helper method to get optional valid marks or -1 to skip
    private static int getOptionalMarksInput(String prompt) {
        int marks = -2; // Start with invalid value that isn't -1
        while ((marks < 0 || marks > 100) && marks != -1) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine();
                if (input.trim().isEmpty()) {
                    return -1; // Default skip
                }
                marks = Integer.parseInt(input);
                if ((marks < 0 || marks > 100) && marks != -1) {
                    System.out.println("Marks must be between 0 and 100, or -1 to skip.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
        return marks;
    }
}
