// In src/main/java/com/example/Main.java
package com.example;

import com.example.model.Course;
import com.example.model.Student;
import com.example.service.FeeService;
import com.example.service.StudentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    // Spring Boot will inject the services for us
    @Autowired
    private StudentManagementService studentService;
    
    @Autowired
    private FeeService feeService;

    public static void main(String[] args) {
        // This line starts the entire Spring Boot application
        SpringApplication.run(Main.class, args);
    }

    // This method runs automatically after the application starts
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Spring + Hibernate Application Started.");
        System.out.println("----------------------------------------");

        try {
            setupInitialData();
            runConsoleMenu();
        } catch (Exception e) {
            System.err.println("An error occurred during runtime: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Application finished. Exiting...");
    }

    private void setupInitialData() {
        System.out.println("Adding initial data...");
        // Add Courses
        Course c1 = studentService.addNewCourse("Computer Science", 48);
        Course c2 = studentService.addNewCourse("Business Administration", 36);

        // Add Students
        Student s1 = studentService.enrollStudent("Alice Smith", new BigDecimal("1000.00"));
        Student s2 = studentService.enrollStudent("Bob Johnson", new BigDecimal("500.00"));

        // Assign Courses
        studentService.assignCourseToStudent(s1.getId(), c1.getId());
        studentService.assignCourseToStudent(s2.getId(), c2.getId());

        System.out.println("Initial data setup complete.");
        System.out.println("----------------------------------------");
    }

    private void runConsoleMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Student Management System Menu ===");
            System.out.println("1. List All Students");
            System.out.println("2. List All Courses");
            System.out.println("3. Make a Payment (Successful)");
            System.out.println("4. Make a Payment (Fail - Insufficient Funds)");
            System.out.println("5. Issue a Refund");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        listStudents();
                        break;
                    case 2:
                        listCourses();
                        break;
                    case 3:
                        System.out.println("\n--- [Test: Successful Payment] ---");
                        System.out.println("Making a $200 payment for Alice (ID 1, Balance $1000)...");
                        feeService.makePayment(1, new BigDecimal("200.00"));
                        System.out.println("Payment successful!");
                        listStudent(1);
                        break;
                    case 4:
                        System.out.println("\n--- [Test: Failed Payment (Rollback)] ---");
                        System.out.println("Making a $600 payment for Bob (ID 2, Balance $500)...");
                        try {
                            feeService.makePayment(2, new BigDecimal("600.00"));
                        } catch (Exception e) {
                            System.err.println("TRANSACTION ROLLED BACK! Error: " + e.getMessage());
                        }
                        System.out.println("After failed attempt:");
                        listStudent(2); // Balance should still be $500
                        break;
                    case 5:
                        System.out.println("\n--- [Test: Issue Refund] ---");
                        System.out.println("Issuing a $100 refund for Bob (ID 2)...");
                        feeService.issueRefund(2, new BigDecimal("100.00"));
                        System.out.println("Refund successful!");
                        listStudent(2);
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void listStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.findAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    private void listStudent(int id) {
        Student s = studentService.getStudentDetails(id);
        System.out.println("Current Details: " + s);
    }
    
    private void listCourses() {
        System.out.println("\n--- All Courses ---");
        List<Course> courses = studentService.findAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            courses.forEach(System.out::println);
        }
    }
}