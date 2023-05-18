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
    @Select("SELECT * FROM user WHERE email = '${email}' AND school='${school}'")
    User selectByIDAndSchool(@Param("email") String email, @Param("school") String school);

    @Select("SELECT * FROM user WHERE id=#{id} AND school=#{school}")
    User selectByStuIdAndSchool(@Param("id") Long id, @Param("school") String school);

    @Insert("INSERT INTO user(`index`,id,name,password,age,gender,email,school,role,status,avatar) VALUES " +
            "(#{index},#{id},#{name},#{password},#{age},#{gender},#{email},#{school},#{role},#{status},#{avatar})")
    int insertUser(@Param("index") Long index,@Param("id") Long id,@Param("name") String name,
                   @Param("password") String password,@Param("age") int age,@Param("gender") String gender,
                   @Param("email") String email,@Param("school")String school,
    @Param("role") Byte role,@Param("status") Byte status,@Param("avatar") String avatar);

    @Select("SELECT * FROM user WHERE `index`=${index}")
    User selectByIndex(@Param("index") Long index);

    //查询学生个人信息，以及在该门课程中的各个实验项目得分
    @Select("SELECT * FROM user JOIN experiment_submit USING(`index`) JOIN experiment_review USING(report_id) " +
            "WHERE user.index=#{index} AND course_id=#{courseId}")
    @Results(
            {
                    //column为数据库字段名，property为实体类字段名
                    @Result(column = "id",property = "id"),
                    @Result(column = "name",property = "name"),
                    @Result(column = "gender",property = "gender"),
                    @Result(column = "school",property = "school"),
                    @Result(column="avatar", property = "avatar")
            }
    )
    StudentCourseInfoDto selectStudentCourseInfoByIndexAndCourseId(@Param("index") Long index, @Param("courseId") Long courseId);

    @Update("UPDATE user SET password='${password}' WHERE email='${email}'")
    Integer updatePassword(@Param("email") String email,@Param("password") String password);

    @Update("UPDATE user SET status='${status}' WHERE email='${email}'")
    Integer updateStatus(@Param("email") String email,@Param("status") Byte status);

    @Update("UPDATE user SET avatar = '${avatar}' WHERE `index`=#{id}")
    void updateUserAvatar(@Param("avatar") String avatar,@Param("id") Long id);



}
