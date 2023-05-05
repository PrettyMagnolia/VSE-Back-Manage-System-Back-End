package com.backend.vse.service.impl;

import com.backend.vse.dto.StudentAttendCourseDto;
import com.backend.vse.dto.StudentInfoDto;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.StudentAttendCourseMapper;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentAttendCourseMapper studentAttendCourseMapper;

    @Autowired
    UserMapper userMapper;
    @Override
    public ArrayList<StudentInfoDto> selectByCourseId(Long courseId) {
        ArrayList<StudentInfoDto> result;
        result=studentAttendCourseMapper.getCourseStudents(courseId);
        return result;
    }

    @Override
    public String addExistStudent(StudentAttendCourseDto existStudent) {
        String result=null;
        try {
            User user;
            user=userMapper.selectByStuIdAndSchool(Long.valueOf(existStudent.getStuId()),existStudent.getSchool());
            studentAttendCourseMapper.insert(user.getIndex(),existStudent.getCourseId());
            result="add success";
        }catch (Exception ex){
            result="add failed";
        }
        return result;
    }

    @Override
    public String deleteExistStudent(StudentAttendCourseDto existStudent) {
        String result=null;
        try {
            User user;
            user=userMapper.selectByStuIdAndSchool(Long.valueOf(existStudent.getStuId()),existStudent.getSchool());
            studentAttendCourseMapper.delete(user.getIndex(),existStudent.getCourseId());
            result="delete success";
        }catch (Exception ex){
            result="delete failed";
        }
        return result;
    }

    @Override
    public ArrayList<StudentInfoDto> selectFreeByCourseId(long courseId) {
        ArrayList<StudentInfoDto> result;
        result=studentAttendCourseMapper.getCourseFreeStudents(courseId);
        return result;
    }
}
