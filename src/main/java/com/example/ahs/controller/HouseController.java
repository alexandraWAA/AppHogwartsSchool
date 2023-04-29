package com.example.ahs.controller;

import com.example.ahs.dto.FacultyDTO;
import com.example.ahs.dto.StudentDTO;
import com.example.ahs.model.Faculty;
import com.example.ahs.service.HouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class HouseController {
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("{id}") // GET http://localhost:8080/facultys/3
    public ResponseEntity<FacultyDTO> getFaculty(@PathVariable Long id) {
        FacultyDTO faculty = houseService.getFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping// GET http://localhost:8080/facultys
    public ResponseEntity<Collection<FacultyDTO>> getFaculties(
            @RequestParam(required = false) String facultyColor,
            @RequestParam(required = false) String facultyName) {
        if(facultyColor != null && !facultyColor.isBlank()) {
            return ResponseEntity.ok(houseService.getFacultyByColor(facultyColor));
        }
        if(facultyName != null && !facultyName.isBlank()) {
            return ResponseEntity.ok(houseService.getFacultyByName(facultyName));
        }
        return ResponseEntity.ok(houseService.getFaculties());
    }

    @GetMapping("{id}/students") // GET http://localhost:8080/facultys/3/students
    public ResponseEntity<Collection<StudentDTO>> getStudentsByFacultyId (@PathVariable Long id) {
        return ResponseEntity.ok(houseService.getStudentsByFacultyId(id));
    }
    @PostMapping// POST http://localhost:8080/facultys
    public ResponseEntity<FacultyDTO> createFaculty(@RequestBody FacultyDTO facultyDTO) {
        FacultyDTO createdFaculty = houseService.createFaculty(facultyDTO);
        return ResponseEntity.ok(createdFaculty);
    }

    @PutMapping// PUT http://localhost:8080/facultys
    public ResponseEntity<FacultyDTO> updateFaculty(@RequestBody FacultyDTO facultyDTO) {
        FacultyDTO updatedFaculty = houseService.updateFaculty(facultyDTO);
        if (updatedFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{id}") // DELETE http://localhost:8080/facultys/3
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        houseService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}
