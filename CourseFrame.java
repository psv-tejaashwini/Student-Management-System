package com.student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CourseFrame extends JFrame {
    private JTextField courseCodeField, courseNameField, creditsField, instructorField;
    private JTextArea descriptionArea;
    private JTable courseTable;
    private CourseDAO courseDAO;

    public CourseFrame() {
        setTitle("Course Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        courseDAO = new CourseDAO();

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Course Code:"), gbc);
        gbc.gridx = 1; courseCodeField = new JTextField(15); inputPanel.add(courseCodeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Course Name:"), gbc);
        gbc.gridx = 1; courseNameField = new JTextField(15); inputPanel.add(courseNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1; creditsField = new JTextField(15); inputPanel.add(creditsField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(new JLabel("Instructor:"), gbc);
        gbc.gridx = 1; instructorField = new JTextField(15); inputPanel.add(instructorField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; descriptionArea = new JTextArea(3, 15); inputPanel.add(new JScrollPane(descriptionArea), gbc);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        saveButton.addActionListener(e -> saveCourse());
        updateButton.addActionListener(e -> updateCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        refreshButton.addActionListener(e -> loadCourses());

        buttonPanel.add(saveButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Code", "Name", "Credits", "Instructor", "Description"};
        courseTable = new JTable(new DefaultTableModel(columnNames, 0));
        add(new JScrollPane(courseTable), BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
        loadCourses();
    }

    private void saveCourse() {
        try {
            Course course = new Course();
            course.setCourseCode(courseCodeField.getText());
            course.setCourseName(courseNameField.getText());
            course.setCredits(Integer.parseInt(creditsField.getText()));
            course.setInstructor(instructorField.getText());
            course.setDescription(descriptionArea.getText());

            courseDAO.addCourse(course);
            JOptionPane.showMessageDialog(this, "Course saved successfully!");
            clearFields();
            loadCourses();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving course: " + e.getMessage());
        }
    }

    private void updateCourse() {
        try {
            Course course = new Course();
            course.setCourseCode(courseCodeField.getText());
            course.setCourseName(courseNameField.getText());
            course.setCredits(Integer.parseInt(creditsField.getText()));
            course.setInstructor(instructorField.getText());
            course.setDescription(descriptionArea.getText());

            courseDAO.updateCourse(course);
            JOptionPane.showMessageDialog(this, "Course updated successfully!");
            clearFields();
            loadCourses();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating course: " + e.getMessage());
        }
    }

    private void deleteCourse() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow >= 0) {
            String courseCode = (String) courseTable.getValueAt(selectedRow, 1);
            try {
                courseDAO.deleteCourse(courseCode);
                JOptionPane.showMessageDialog(this, "Course deleted successfully!");
                loadCourses();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting course: " + e.getMessage());
            }
        }
    }

    private void loadCourses() {
        try {
            java.util.List<Course> courses = courseDAO.getAllCourses();
            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
            model.setRowCount(0);

            for (Course course : courses) {
                Object[] row = {
                    course.getId(),
                    course.getCourseCode(),
                    course.getCourseName(),
                    course.getCredits(),
                    course.getInstructor(),
                    course.getDescription()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage());
        }
    }

    private void clearFields() {
        courseCodeField.setText("");
        courseNameField.setText("");
        creditsField.setText("");
        instructorField.setText("");
        descriptionArea.setText("");
    }
}