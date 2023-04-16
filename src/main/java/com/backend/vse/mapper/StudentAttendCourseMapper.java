package com.backend.vse.mapper;

import com.backend.vse.dto.StudentSimpleDto;
import com.backend.vse.entity.StudentAttendCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface StudentAttendCourseMapper extends BaseMapper<StudentAttendCourse> {
    @Insert("INSERT INTO student_attend_course(`index`,`course_id`) VALUES (#{index},#{courseId})")
    int insert(@Param("index") Long index, @Param("courseId") Long courseId);

    @Select("SELECT user.`index` stuId, user.id schoolNumber, user.name stuName, user.school " +
            "FROM user, student_attend_course sac " +
            "WHERE user.role = 0 AND sac.`index` = user.`index` AND sac.course_id = #{courseId};")
    ArrayList<StudentSimpleDto> getCourseStudents(@Param("courseId") Long courseId);
}
