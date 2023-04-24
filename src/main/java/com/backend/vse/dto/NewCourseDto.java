package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCourseDto {
    String courseName;
    String semester;
    List<Long> teacherList;
    List<ImportedStudentDto> studentList;
    int year;
}
