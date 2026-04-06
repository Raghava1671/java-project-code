package com.studentresult.dao;

import com.studentresult.model.Student;
import com.studentresult.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Student operations.
 */
public class StudentDAO {

    public void addStudent(Student student) {
        String query = "INSERT INTO students (student_id, name, subject1_marks, subject2_marks, subject3_marks, total, percentage, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, student.getStudentId());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getSubject1Marks());
            pstmt.setInt(4, student.getSubject2Marks());
            pstmt.setInt(5, student.getSubject3Marks());
            pstmt.setInt(6, student.getTotal());
            pstmt.setDouble(7, student.getPercentage());
            pstmt.setString(8, student.getGrade());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student record added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    public void updateStudent(Student student) {
        String query = "UPDATE students SET name = ?, subject1_marks = ?, subject2_marks = ?, subject3_marks = ?, total = ?, percentage = ?, grade = ? WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getSubject1Marks());
            pstmt.setInt(3, student.getSubject2Marks());
            pstmt.setInt(4, student.getSubject3Marks());
            pstmt.setInt(5, student.getTotal());
            pstmt.setDouble(6, student.getPercentage());
            pstmt.setString(7, student.getGrade());
            pstmt.setInt(8, student.getStudentId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student record updated successfully!");
            } else {
                System.out.println("Student with ID " + student.getStudentId() + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }

    public void deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student record deleted successfully!");
            } else {
                System.out.println("Student with ID " + studentId + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }

    public Student getStudentById(int studentId) {
        String query = "SELECT * FROM students WHERE student_id = ?";
        Student student = null;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    student = extractStudentFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student: " + e.getMessage());
        }
        return student;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student student = extractStudentFromResultSet(rs);
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setName(rs.getString("name"));
        student.setSubject1Marks(rs.getInt("subject1_marks"));
        student.setSubject2Marks(rs.getInt("subject2_marks"));
        student.setSubject3Marks(rs.getInt("subject3_marks"));
        student.setTotal(rs.getInt("total"));
        student.setPercentage(rs.getDouble("percentage"));
        student.setGrade(rs.getString("grade"));
        return student;
    }
}
