package com.student;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame() {
        setTitle("Student Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu studentMenu = new JMenu("Students");
        JMenu courseMenu = new JMenu("Courses");
        JMenu enrollmentMenu = new JMenu("Enrollments");

        JMenuItem addStudent = new JMenuItem("Add Student");
        JMenuItem viewStudents = new JMenuItem("View Students");
        JMenuItem addCourse = new JMenuItem("Add Course");
        JMenuItem viewCourses = new JMenuItem("View Courses");
        JMenuItem manageEnrollments = new JMenuItem("Manage Enrollments");

        addStudent.addActionListener(e -> new StudentFrame().setVisible(true));
        viewStudents.addActionListener(e -> new StudentFrame().setVisible(true));
        addCourse.addActionListener(e -> new CourseFrame().setVisible(true));
        viewCourses.addActionListener(e -> new CourseFrame().setVisible(true));
        manageEnrollments.addActionListener(e -> new EnrollmentFrame().setVisible(true));

        studentMenu.add(addStudent);
        studentMenu.add(viewStudents);
        courseMenu.add(addCourse);
        courseMenu.add(viewCourses);
        enrollmentMenu.add(manageEnrollments);

        menuBar.add(studentMenu);
        menuBar.add(courseMenu);
        menuBar.add(enrollmentMenu);

        setJMenuBar(menuBar);

        JLabel welcomeLabel = new JLabel("Welcome to Student Management System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
    }
}