// In src/main/java/com/example/service/StudentManagementServiceImpl.java
package com.example.service;

import com.example.dao.CourseRepository;
import com.example.dao.StudentRepository;
import com.example.model.Course;
import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StudentManagementServiceImpl implements StudentManagementService {

    @Autowired
    private StudentRepository studentRepository; // Use Repository

    @Autowired
    private CourseRepository courseRepository; // Use Repository

    @Override
    @Transactional
    public Student enrollStudent(String name, BigDecimal initialBalance) {
        Student student = new Student(name, initialBalance);
        studentRepository.save(student); // Use save()
        System.out.println("Enrolled new student: " + name);
        return student;
    }

    @Override
    @Transactional
    public Course addNewCourse(String courseName, int duration) {
        Course course = new Course(courseName, duration);
        courseRepository.save(course); // Use save()
        System.out.println("Added new course: " + courseName);
        return course;
    }

    @Override
    @Transactional
    public void assignCourseToStudent(int studentId, int courseId) {
        // Use findById().orElse(null)
        Student student = studentRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (student != null && course != null) {
            student.setCourse(course);
            studentRepository.save(student); // Use save() for updates too
            System.out.println("Assigned " + course.getCourseName() + " to " + student.getName());
        } else {
            System.err.println("Error: Student or Course not found.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentDetails(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllStudents() {
        return studentRepository.findAll(); // Use findAll()
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAllCourses() {
        return courseRepository.findAll(); // Use findAll()
    }
}