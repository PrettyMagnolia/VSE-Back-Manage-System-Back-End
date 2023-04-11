package com.backend.vse.mapper;

import com.backend.vse.dto.StudentCourseInfoDto;
import com.backend.vse.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/3/30
 * @JDKVersion 17.0.4
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE email = '${email}' AND school='${school}' AND password='${password}'")
    User selectByIDAndSchool(@Param("email") String email, @Param("school") String school, @Param("password") String password);
    @Select("SELECT * FROM user WHERE `index`=${index}")
    User selectByIndex(@Param("index") Long index);

    //查询学生个人信息，以及在该门课程中的各个实验项目得分
    @Select("SELECT * FROM user JOIN experiment_submit USING(`index`) JOIN experiment_review USING(report_id) " +
            "WHERE user.index=#{index} AND course_id=#{courseId}")
    @Results(
            {
                    //column为数据库字段名，property为实体类字段名
                    @Result(column = "index",property = "index"),
                    @Result(column = "id",property = "id"),
                    @Result(column = "name",property = "name"),
                    @Result(column = "age",property = "age"),
                    @Result(column = "gender",property = "gender"),
                    @Result(column = "email",property = "email"),
                    @Result(column = "school",property = "school"),
//                    @Result(column = "index,course_id,experiment_id",property = "experimentScoreList",javaType = List.class,
//                            one=@One(select = "com.backend.vse.mapper.ExperimentMapper.selectExperimentScoreByIndexAndCourseAndExperimentId")
//                    )
            }
    )

    @Update("UPDATE user SET password='${password}' WHERE email='${email}'")
    Integer updatePassword(@Param("email") String email,@Param("password") String password);

    @Update("UPDATE user SET status='${status}' WHERE email='${email}'")
    Integer updateStatus(@Param("email") String email,@Param("status") Byte status);
    StudentCourseInfoDto selectStudentCourseInfoByIndexAndCourseId(@Param("index") Long index, @Param("courseId") Long courseId);


}
