package com.student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EnrollmentFrame extends JFrame {
    private JComboBox<String> studentComboBox;
    private JComboBox<String> courseComboBox;
    private JTextField gradeField;
    private JTable enrollmentTable;
    private EnrollmentDAO enrollmentDAO;

    public EnrollmentFrame() {
        setTitle("Enrollment Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        enrollmentDAO = new EnrollmentDAO();

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1; studentComboBox = new JComboBox<>(); inputPanel.add(studentComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Course Code:"), gbc);
        gbc.gridx = 1; courseComboBox = new JComboBox<>(); inputPanel.add(courseComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Grade:"), gbc);
        gbc.gridx = 1; gradeField = new JTextField(15); inputPanel.add(gradeField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton enrollButton = new JButton("Enroll");
        JButton updateButton = new JButton("Update Grade");
        JButton unenrollButton = new JButton("Unenroll");

        enrollButton.addActionListener(e -> enrollStudent());
        updateButton.addActionListener(e -> updateGrade());
        unenrollButton.addActionListener(e -> unenrollStudent());

        buttonPanel.add(enrollButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(unenrollButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Student ID", "Course Code", "Grade", "Status"};
        enrollmentTable = new JTable(new DefaultTableModel(columnNames, 0));
        add(new JScrollPane(enrollmentTable), BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
        loadStudentsAndCourses();
        loadEnrollments();
    }

    private void loadStudentsAndCourses() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            java.util.List<Student> students = studentDAO.getAllStudents();
            studentComboBox.removeAllItems();
            for (Student student : students) {
                studentComboBox.addItem(student.getStudentId());
            }

            CourseDAO courseDAO = new CourseDAO();
            java.util.List<Course> courses = courseDAO.getAllCourses();
            courseComboBox.removeAllItems();
            for (Course course : courses) {
                courseComboBox.addItem(course.getCourseCode());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void enrollStudent() {
        try {
            String studentId = (String) studentComboBox.getSelectedItem();
            String courseCode = (String) courseComboBox.getSelectedItem();

            Enrollment enrollment = new Enrollment();
            enrollment.setStudentId(studentId);
            enrollment.setCourseCode(courseCode);
            enrollment.setGrade("N/A");

            enrollmentDAO.enrollStudent(enrollment);
            JOptionPane.showMessageDialog(this, "Student enrolled successfully!");
            loadEnrollments();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error enrolling student: " + e.getMessage());
        }
    }

    private void updateGrade() {
        try {
            int selectedRow = enrollmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                String enrollmentId = enrollmentTable.getValueAt(selectedRow, 0).toString();
                String grade = gradeField.getText();

                enrollmentDAO.updateGrade(enrollmentId, grade);
                JOptionPane.showMessageDialog(this, "Grade updated successfully!");
                loadEnrollments();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating grade: " + e.getMessage());
        }
    }

    private void unenrollStudent() {
        try {
            int selectedRow = enrollmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                String enrollmentId = enrollmentTable.getValueAt(selectedRow, 0).toString();

                enrollmentDAO.unenrollStudent(enrollmentId);
                JOptionPane.showMessageDialog(this, "Student unenrolled successfully!");
                loadEnrollments();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error unenrolling student: " + e.getMessage());
        }
    }

    private void loadEnrollments() {
        try {
            java.util.List<Enrollment> enrollments = enrollmentDAO.getAllEnrollments();
            DefaultTableModel model = (DefaultTableModel) enrollmentTable.getModel();
            model.setRowCount(0);

            for (Enrollment enrollment : enrollments) {
                Object[] row = {
                    enrollment.getId(),
                    enrollment.getStudentId(),
                    enrollment.getCourseCode(),
                    enrollment.getGrade(),
                    enrollment.getStatus()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading enrollments: " + e.getMessage());
        }
    }
}