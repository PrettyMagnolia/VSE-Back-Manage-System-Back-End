package com.backend.vse.mapper;

import com.backend.vse.entity.StudentAttendCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentAttendCourseMapper extends BaseMapper<StudentAttendCourse> {
    @Insert("INSERT INTO student_attend_course(`index`,`course_id`) VALUES (#{index},#{courseId})")
    int insert(@Param("index") Long index, @Param("courseId") Long courseId);
}
