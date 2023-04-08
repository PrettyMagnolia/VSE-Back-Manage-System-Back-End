package com.backend.vse.mapper;

import com.backend.vse.entity.User;
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
public interface UserMapper {
    @Select("SELECT * FROM user WHERE email = '${email}' AND school='${school}' AND password='${password}'")
    User selectByIDAndSchool(@Param("email") String email, @Param("school") String school, @Param("password") String password);

    @Select("SELECT * FROM user WHERE `index` = ${index}")
    User selectByIndex(@Param("index") Long index);

    @Update("UPDATE user SET password='${password}' WHERE email='${email}'")
    Integer updatePassword(@Param("email") String email,@Param("password") String password);

    @Update("UPDATE user SET status='${status}' WHERE email='${email}'")
    Integer updateStatus(@Param("email") String email,@Param("status") Byte status);
}
