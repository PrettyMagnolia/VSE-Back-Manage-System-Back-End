package com.backend.vse.service.impl;

import com.backend.vse.dto.StudentSimpleDto;
import com.backend.vse.mapper.StudentAttendCourseMapper;
import com.backend.vse.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentAttendCourseMapper studentAttendCourseMapper;
    @Override
    public ArrayList<StudentSimpleDto> selectByCourseId(Long courseId) {
        ArrayList<StudentSimpleDto> result=new ArrayList<>();
        result=studentAttendCourseMapper.getCourseStudents(courseId);
        return result;
    }
}
