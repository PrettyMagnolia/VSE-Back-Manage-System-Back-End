package com.backend.vse.mapper;

import com.backend.vse.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;



@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    @Select("SELECT c.* FROM course c, teacher_teach_course tt WHERE c.course_id=tt.course_id AND tt.index='${teacher_id}'")
    List<Course> getTeachingCourseList(@Param("teacher_id") long teacherId);
    @Select("SELECT * FROM course JOIN teacher_teach_course USING(course_id) WHERE `index`=#{index}")
    List<Course> getCoursesBySemester(@Param("index") Long index);
}

