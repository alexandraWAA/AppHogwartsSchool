package com.example.ahs.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "faculty_name")
    private String facultyName;
    @Column(name = "faculty_color")
    private String facultyColor;
    @OneToMany(mappedBy = "faculty")
    private List<Student> students;

//    public Faculty() {
//    }
//
//    public Faculty(Long facultyId, String facultyName, String facultyColor) {
//        this.facultyId = facultyId;
//        this.facultyName = facultyName;
//        this.facultyColor = facultyColor;
//    }
//
//    public Long getFacultyId() {
//        return facultyId;
//    }
//
//    public void setFacultyId(Long facultyId) {
//        this.facultyId = facultyId;
//    }
//
//    public String getFacultyName() {
//        return facultyName;
//    }
//
//    public void setFacultyName(String facultyName) {
//        this.facultyName = facultyName;
//    }
//
//    public String getFacultyColor() {
//        return facultyColor;
//    }
//
//    public void setFacultyColor(String facultyColor) {
//        this.facultyColor = facultyColor;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Faculty)) return false;
//        Faculty faculty = (Faculty) o;
//        return Objects.equals(getFacultyId(), faculty.getFacultyId()) && Objects.equals(getFacultyName(), faculty.getFacultyName()) && Objects.equals(getFacultyColor(), faculty.getFacultyColor());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getFacultyId(), getFacultyName(), getFacultyColor());
//    }
//
//    @Override
//    public String toString() {
//        return "Faculty{" +
//                "facultyId=" + facultyId +
//                ", facultyName='" + facultyName + '\'' +
//                ", facultyColor='" + facultyColor + '\'' +
//                '}';
//    }
}