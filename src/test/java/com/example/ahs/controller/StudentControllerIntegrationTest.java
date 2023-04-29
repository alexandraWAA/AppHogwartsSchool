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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Testcontainers
public class StudentControllerIntegrationTest extends TestConfiguration {
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
    private final JSONObject jsonObject = new JSONObject();
    private Student student = new Student();

    @BeforeEach
    public void setUp() throws JSONException {
        faculty.setFacultyName("Гриффиндор");
        faculty.setFacultyColor("красный");
        faculty.setStudents(new ArrayList<>());
        facultyRepository.save(faculty);


        jsonObject.put("studentId", student.getId());
        jsonObject.put("studentName", "Гарри");
        jsonObject.put("studentAge", 12);
        jsonObject.put("facultyId", faculty.getId());


        student.setStudentName("Рон");
        student.setStudentAge(13);
        student.setFaculty(faculty);
        studentRepository.save(student);
    }
    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }


    @Test
    void testCreateStudent() throws Exception {

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.studentName").value("Гарри"))
                .andExpect(jsonPath("$.studentAge").value(12))
                .andExpect(jsonPath("$.facultyId").value(faculty.getId()));

        mockMvc.perform(get("/students?pageNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].studentName").value("Гарри"))
                .andExpect(jsonPath("$[1].studentAge").value(12))
                .andExpect(jsonPath("$[1].facultyId").value(faculty.getId()));
    }

    @Test
    void testUpdateStudent() throws Exception {

        jsonObject.put("studentName", "Рон");
        jsonObject.put("studentAge", 15);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.studentName").value("Рон"))
                .andExpect(jsonPath("$.studentAge").value(15))
                .andExpect(jsonPath("$.facultyId").value(faculty.getId()));

        mockMvc.perform(get("/students?pageNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].studentName").value("Рон"))
                .andExpect(jsonPath("$[1].studentAge").value(15))
                .andExpect(jsonPath("$[1].facultyId").value(faculty.getId()));
    }

    @Test
    void testDeleteStudent() throws Exception {

        mockMvc.perform(delete("/students/" + student.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/students?pageNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetStudent() throws Exception {

        mockMvc.perform(get("/students/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value("Рон"))
                .andExpect(jsonPath("$.studentAge").value(13))
                .andExpect(jsonPath("$.facultyId").value(faculty.getId()));
    }

    @Test
    void testGetListOfStudents() throws Exception {

        mockMvc.perform(get("/students?pageNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetFacultyByStudentId() throws Exception {
        jsonObject.put("facultyName", "Гриффиндор");
        jsonObject.put("facultyColor", "красный");
        jsonObject.put("id", faculty.getId());

        mockMvc.perform(get("/students/" + student.getId()+ "/faculty/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.facultyName").value("Гриффиндор"))
                .andExpect(jsonPath("$.facultyColor").value("красный"))
                .andExpect(jsonPath("$.id").value(faculty.getId()));
    }

}
