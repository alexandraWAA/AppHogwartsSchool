package com.example.ahs.service;

import com.example.ahs.dto.FacultyDTO;
import com.example.ahs.dto.StudentDTO;
import com.example.ahs.model.Faculty;
import com.example.ahs.model.Student;
import com.example.ahs.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {
    private FacultyRepository facultyRepository;
    public static final Logger logger = LoggerFactory.getLogger(HouseService.class);
    public HouseService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public FacultyDTO createFaculty(FacultyDTO facultyDTO){
        logger.info("Creating a new faculty");
        Faculty faculty = facultyDTO.toFaculty();
        Faculty createdFaculty = facultyRepository.save(faculty);
        logger.info("New faculty has been created");
        return FacultyDTO.fromFaculty(createdFaculty);
    }

    public FacultyDTO findFaculty(Long id){
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty != null){
            return FacultyDTO.fromFaculty(faculty);
        }
        return null;
    }

    public FacultyDTO updateFaculty(FacultyDTO facultyDTO){
        logger.info("Updating a faculty");
        Faculty faculty = facultyDTO.toFaculty();
        Faculty updatedFaculty = facultyRepository.save(faculty);
        logger.info("Faculty has been updated");
        return FacultyDTO.fromFaculty(updatedFaculty);
    }

    public void deleteFaculty(Long id) {
        logger.info("Deleting faculty with id: " + id);
        facultyRepository.deleteById(id);
        logger.info("Faculty with id: " + id + " has been deleted");
    }

    public FacultyDTO getFacultyById(Long id) {
        logger.info("Getting faculty with id: " + id);
        return FacultyDTO.fromFaculty(facultyRepository.findById(id).get());
    }
    public Collection<FacultyDTO> getFaculties() {
        logger.info("Getting all faculties");
        return facultyRepository.findAll()
                .stream()
                .map(FacultyDTO::fromFaculty)
                .collect(Collectors.toList());
    }
    public Collection<FacultyDTO> getFacultyByColor(String facultyColor){
        logger.info("Getting all faculties by color: " + facultyColor);
        return facultyRepository.findByFacultyColorIgnoreCase(facultyColor)
                .stream()
                .map(FacultyDTO::fromFaculty)
                .collect(Collectors.toList()); }

    public Collection<FacultyDTO> getFacultyByName (String facultyName) {
        logger.info("Getting faculty by name: " + facultyName);
        return facultyRepository.findByFacultyNameContainsIgnoreCase(facultyName).stream()
                .map(FacultyDTO::fromFaculty)
                .collect(Collectors.toList());
    }

    public Collection<StudentDTO> getStudentsByFacultyId(Long id) {
        logger.info("Getting all students by faculty with id: " + id);
        List<Student> students = facultyRepository.findById(id).get().getStudents();
        List<StudentDTO> studentsDTO = new ArrayList<>();
        for(Student student : students) {
            StudentDTO studentDTO = StudentDTO.fromStudent(student);
            studentsDTO.add(studentDTO);
        }
        return studentsDTO;
    }
}
