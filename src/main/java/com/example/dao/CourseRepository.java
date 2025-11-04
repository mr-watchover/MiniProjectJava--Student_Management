// In src/main/java/com/example/dao/CourseRepository.java
package com.example.dao;

import com.example.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    // Spring Data JPA automatically creates methods like save(), findById(), findAll()
}