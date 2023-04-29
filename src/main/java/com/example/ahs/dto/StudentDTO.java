package com.example.ahs.dto;

import com.example.ahs.model.Student;
import lombok.Data;
@Data
public class StudentDTO {
    private Long id;
    private String studentName;
    private Integer studentAge;
    private Long facultyId;


    public static StudentDTO fromStudent(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setStudentName(student.getStudentName());
        studentDTO.setStudentAge(student.getStudentAge());
        studentDTO.setFacultyId(student.getFaculty().getId());
        return studentDTO;
    }

    public Student toStudent() {
        Student student = new Student();
        student.setId(this.getId());
        student.setStudentName(this.getStudentName());
        student.setStudentAge(this.getStudentAge());
        return student;
    }
}
