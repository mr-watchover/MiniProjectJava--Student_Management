package com.example.service;

import com.example.model.Course;
import com.example.model.Student;
import java.math.BigDecimal;
import java.util.List;

public interface StudentManagementService {

    /**
     * Creates a new student and saves them to the DB.
     */
    Student enrollStudent(String name, BigDecimal initialBalance);
    
    /**
     * Creates a new course and saves it to the DB.
     */
    Course addNewCourse(String courseName, int duration);

    /**
     * Assigns an existing course to an existing student.
     */
    void assignCourseToStudent(int studentId, int courseId);

    /**
     * Get a single student's details.
     */
    Student getStudentDetails(int studentId);

    /**
     * Get all registered students.
     */
    List<Student> findAllStudents();

    /**
     * Get all available courses.
     */
    List<Course> findAllCourses();
}