// Place in: src/main/java/com/example/model/Course.java
package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "duration")
    private int durationInMonths;

    // --- Constructors ---
    public Course() {}

    public Course(String courseName, int durationInMonths) {
        this.courseName = courseName;
        this.durationInMonths = durationInMonths;
    }

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getDurationInMonths() { return durationInMonths; }
    public void setDurationInMonths(int durationInMonths) { this.durationInMonths = durationInMonths; }

    @Override
    public String toString() {
        return "Course [id=" + id + ", courseName=" + courseName + ", durationInMonths=" + durationInMonths + "]";
    }
}