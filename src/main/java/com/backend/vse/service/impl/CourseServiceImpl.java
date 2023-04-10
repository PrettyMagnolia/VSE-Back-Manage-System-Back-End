package com.backend.vse.service.impl;

import com.backend.vse.entity.Course;
import com.backend.vse.entity.StudentAttendCourse;
import com.backend.vse.entity.TeacherTeachCourse;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.CourseMapper;
import com.backend.vse.mapper.StudentAttendCourseMapper;
import com.backend.vse.mapper.TeacherTeachCourseMapper;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherTeachCourseMapper teacherTeachCourseMapper;
    @Autowired
    StudentAttendCourseMapper studentAttendCourseMapper;
    @Autowired
    UserMapper userMapper;


    @Override
    public int insertOneCourse(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public int insertOneTeach(TeacherTeachCourse teacherTeachCourse) {
        //如果没有传角色，就在这里填补角色
        if(teacherTeachCourse.getRole() == null){
            Long index = teacherTeachCourse.getIndex();
            User user = userMapper.selectByIndex(index);
            Byte role = user.getRole();
            teacherTeachCourse.setRole(role);
        }
        return teacherTeachCourseMapper.insert(teacherTeachCourse.getIndex(),teacherTeachCourse.getCourseId(), teacherTeachCourse.getRole());
    }

    @Override
    public int insertOneAttend(StudentAttendCourse studentAttendCourse) {
        return studentAttendCourseMapper.insert(studentAttendCourse.getIndex(),studentAttendCourse.getCourseId());
    }
}
