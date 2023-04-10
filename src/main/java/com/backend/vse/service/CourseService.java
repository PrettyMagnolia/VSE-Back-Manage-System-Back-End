package com.backend.vse.service;

import com.backend.vse.entity.Course;
import com.backend.vse.entity.StudentAttendCourse;
import com.backend.vse.entity.TeacherTeachCourse;
import com.backend.vse.mapper.StudentAttendCourseMapper;
import com.backend.vse.mapper.TeacherTeachCourseMapper;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    int insertOneCourse(Course course);
    int insertOneTeach(TeacherTeachCourse teacherTeachCourse);
    int insertOneAttend(StudentAttendCourse studentAttendCourse);
}
