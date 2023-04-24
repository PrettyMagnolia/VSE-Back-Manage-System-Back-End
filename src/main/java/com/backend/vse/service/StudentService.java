package com.backend.vse.service;

import com.backend.vse.dto.StudentAttendCourseDto;
import com.backend.vse.dto.StudentInfoDto;
import com.backend.vse.dto.StudentSimpleDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface StudentService {
    ArrayList<StudentInfoDto> selectByCourseId(Long courseId);

    String addExistStudent(StudentAttendCourseDto existStudent);

    String deleteExistStudent(StudentAttendCourseDto existStudent);

    ArrayList<StudentInfoDto> selectFreeByCourseId(long courseId);
}
