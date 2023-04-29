package com.example.ahs.controller;

import com.example.ahs.config.TestConfiguration;
import com.example.ahs.model.Faculty;
import com.example.ahs.model.Student;
import com.example.ahs.repository.FacultyRepository;
import com.example.ahs.repository.StudentRepository;
import com.example.ahs.service.StudentService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Testcontainers
class HouseControllerIntegrationTest extends TestConfiguration {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    StudentController studentController;

    private Faculty faculty = new Faculty();
    private Student student = new Student();

    private final JSONObject jsonObject = new JSONObject();


    @BeforeEach
    public void setUp() throws JSONException {

        faculty.setFacultyName("Звездочки");
        faculty.setFacultyColor("золотой");
        facultyRepository.save(faculty);

        jsonObject.put("facultyName", "Слизерин");
        jsonObject.put("facultyColor", "зеленый");

        Student student1 = new Student();
        student1.setStudentName("Geil");
        student1.setStudentAge(12);
        student1.setFaculty(faculty);
        studentRepository.save(student1);
        Student student2 = new Student();
        student2.setStudentName("Tom");
        student2.setStudentAge(21);
        student2.setFaculty(faculty);
        studentRepository.save(student2);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        faculty.setStudents(students);
    }
    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    public void testCreateFaculty() throws Exception {

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.facultyName").value("Слизерин"))
                .andExpect(jsonPath("$.facultyColor").value("зеленый"));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].facultyName").value("Слизерин"))
                .andExpect(jsonPath("$[1].facultyColor").value("зеленый"));
    }
    @Test
    public void testUpdateFaculty() throws Exception {
        jsonObject.put("facultyName", "Слизерин");
        jsonObject.put("facultyColor", "зеленый");

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.facultyName").value("Слизерин"))
                .andExpect(jsonPath("$.facultyColor").value("зеленый"));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].facultyName").value("Слизерин"))
                .andExpect(jsonPath("$[1].facultyColor").value("зеленый"));
    }
    @Test
    public void testDeleteFaculty() throws Exception {
        studentRepository.deleteAll();

        mockMvc.perform(delete("/faculty/" + faculty.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetFaculty() throws Exception {

        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.facultyName").value("Звездочки"))
                .andExpect(jsonPath("$.facultyColor").value("золотой"));
    }

    @Test
    public void testGetFacultyWhenFacultyIsEmpty() throws Exception {
        studentRepository.deleteAll();
        faculty.setStudents(Collections.emptyList());
        facultyRepository.delete(faculty);

        mockMvc.perform(get("/faculty/get/" + faculty.getId()))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetListFaculty() throws Exception {

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetFacultiesByColor() throws Exception {
        String facultyColor = "green";

        mockMvc.perform(get("/faculty?definiteColor=" + facultyColor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].facultyName").value(faculty.getFacultyName()))
                .andExpect(jsonPath("$[0].facultyColor").value(faculty.getFacultyColor()));
    }

    @Test
    public void testGetFacultiesByName() throws Exception {

        mockMvc.perform(get("/faculty?name=" + faculty.getFacultyName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].facultyName").value(faculty.getFacultyName()))
                .andExpect(jsonPath("$[0].facultyColor").value(faculty.getFacultyColor()));
    }

    @Test
    public void getAllStudentsByFacultyId() throws Exception {

        mockMvc.perform(get("/faculty/" + faculty.getId() + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(faculty.getStudents().size()));
    }
}
