package com.student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class StudentFrame extends JFrame {
    private JTextField studentIdField, firstNameField, lastNameField, emailField, phoneField, courseField;
    private JTable studentTable;
    private StudentDAO studentDAO;

    public StudentFrame() {
        setTitle("Student Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        studentDAO = new StudentDAO();

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Input fields
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1; studentIdField = new JTextField(15); inputPanel.add(studentIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; firstNameField = new JTextField(15); inputPanel.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; lastNameField = new JTextField(15); inputPanel.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; emailField = new JTextField(15); inputPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; phoneField = new JTextField(15); inputPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; inputPanel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1; courseField = new JTextField(15); inputPanel.add(courseField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        saveButton.addActionListener(e -> saveStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        refreshButton.addActionListener(e -> loadStudents());

        buttonPanel.add(saveButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Table for displaying students
        String[] columnNames = {"ID", "Student ID", "First Name", "Last Name", "Email", "Phone", "Course"};
        studentTable = new JTable(new DefaultTableModel(columnNames, 0));
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
        loadStudents();
    }

    private void saveStudent() {
        try {
            Student student = new Student();
            student.setStudentId(studentIdField.getText());
            student.setFirstName(firstNameField.getText());
            student.setLastName(lastNameField.getText());
            student.setEmail(emailField.getText());
            student.setPhone(phoneField.getText());
            student.setCourse(courseField.getText());

            studentDAO.addStudent(student);
            JOptionPane.showMessageDialog(this, "Student saved successfully!");
            clearFields();
            loadStudents();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving student: " + e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            Student student = new Student();
            student.setStudentId(studentIdField.getText());
            student.setFirstName(firstNameField.getText());
            student.setLastName(lastNameField.getText());
            student.setEmail(emailField.getText());
            student.setPhone(phoneField.getText());
            student.setCourse(courseField.getText());

            studentDAO.updateStudent(student);
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
            clearFields();
            loadStudents();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating student: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            String studentId = (String) studentTable.getValueAt(selectedRow, 1);
            try {
                studentDAO.deleteStudent(studentId);
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                loadStudents();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage());
            }
        }
    }

    private void loadStudents() {
        try {
            java.util.List<Student> students = studentDAO.getAllStudents();
            DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
            model.setRowCount(0);

            for (Student student : students) {
                Object[] row = {
                    student.getId(),
                    student.getStudentId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getEmail(),
                    student.getPhone(),
                    student.getCourse()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }

    private void clearFields() {
        studentIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        courseField.setText("");
    }
}