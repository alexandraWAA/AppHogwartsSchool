package com.example.ahs.repository;

import com.example.ahs.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByStudentAge(Integer studentAge);
    List<Student> findByStudentAgeBetween(Integer studentAgeMin, Integer studentAgeMax);
    @Query(value = "SELECT SUM(sum) AS count FROM student AS sum", nativeQuery = true)
    Long getSumStudentAge();

    @Query(value = "SELECT AVG(student_age) FROM student AS sum", nativeQuery = true)
    Long getAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY student_age ASC LIMIT 5", nativeQuery = true)
    Collection<Student> getFiveYoungestStudents();
}
