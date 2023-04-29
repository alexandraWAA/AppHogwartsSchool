package com.example.ahs.repository;


import com.example.ahs.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByFacultyColorIgnoreCase(String facultyColor);
    List<Faculty> findByFacultyNameContainsIgnoreCase(String facultyName);
}
