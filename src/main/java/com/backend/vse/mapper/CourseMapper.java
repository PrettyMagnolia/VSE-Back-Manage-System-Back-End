package com.backend.vse.mapper;

import com.backend.vse.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("SELECT c.* FROM course c, teacher_teach_course tt WHERE c.course_id=tt.course_id AND tt.index='${teacher_id}'")
    List<Course> getTeachingCourseList(@Param("teacher_id") long teacherId);
}
