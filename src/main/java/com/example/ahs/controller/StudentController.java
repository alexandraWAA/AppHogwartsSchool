package com.example.ahs.controller;

import com.example.ahs.dto.FacultyDTO;
import com.example.ahs.dto.StudentDTO;
import com.example.ahs.model.Student;
import com.example.ahs.service.StudentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")// GET http://localhost:8080/students/3
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping // GET http://localhost:8080/students
    public ResponseEntity<Collection<StudentDTO>> getStudents(@RequestParam(required = false) Integer studentAge,
                                                              @RequestParam(required = false) Integer studentAgeMin, @RequestParam(required = false) Integer studentAgeMax,
                                                              @PageableDefault(size=50) Pageable pageable) {
        if (studentAge != null) {
            return ResponseEntity.ok(studentService.getStudentsByAge(studentAge));
        }
        if (studentAgeMin != null && studentAgeMax != null) {
            return ResponseEntity.ok(studentService.getStudentsByAgeBetween(studentAgeMin, studentAgeMax));
        }
        return ResponseEntity.ok(studentService.getStudents(pageable));
    }

    @GetMapping("{id}/faculty") // GET http://localhost:8080/students/3/students
    public ResponseEntity<FacultyDTO> getFacultyByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getFacultyByStudentId(id));
    }
    @GetMapping("/sumAges")
    public ResponseEntity<Long> getSumStudentAge() {
        return ResponseEntity.ok(studentService.getSumStudentAge());
    }

    @GetMapping("/averageAge")
    public ResponseEntity<Long> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("/youngestStudents")
    public ResponseEntity<Collection<StudentDTO>> getFiveYoungestStudents() {
        return ResponseEntity.ok(studentService.getFiveYoungestStudents());
    }
    @PostMapping// POST http://localhost:8080/students
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.ok(createdStudent);
    }

    @PutMapping// PUT http://localhost:8080/students
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(studentDTO);
        if (updatedStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")// DELETE http://localhost:8080/students/3
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
