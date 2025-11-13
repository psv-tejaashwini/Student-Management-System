package com.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
    public void enrollStudent(Enrollment enrollment) throws SQLException {
        String sql = "INSERT INTO enrollments (student_id, course_code, grade) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enrollment.getStudentId());
            pstmt.setString(2, enrollment.getCourseCode());
            pstmt.setString(3, enrollment.getGrade());
            pstmt.executeUpdate();
        }
    }

    public void updateGrade(String enrollmentId, String grade) throws SQLException {
        String sql = "UPDATE enrollments SET grade=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, grade);
            pstmt.setString(2, enrollmentId);
            pstmt.executeUpdate();
        }
    }

    public void unenrollStudent(String enrollmentId) throws SQLException {
        String sql = "DELETE FROM enrollments WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enrollmentId);
            pstmt.executeUpdate();
        }
    }

    public List<Enrollment> getAllEnrollments() throws SQLException {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments";
        try (Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setId(rs.getString("id"));
                enrollment.setStudentId(rs.getString("student_id"));
                enrollment.setCourseCode(rs.getString("course_code"));
                enrollment.setGrade(rs.getString("grade"));
                enrollment.setStatus(rs.getString("status"));
                enrollments.add(enrollment);
            }
        }
        return enrollments;
    }
}