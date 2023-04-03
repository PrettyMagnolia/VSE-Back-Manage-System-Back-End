package com.backend.vse.mapper;

import com.backend.vse.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/3/30
 * @JDKVersion 17.0.4
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where email = '${email}' and school='${school}' and password='${password}'")
    User selectByIDAndSchool(@Param("email") String email, @Param("school") String school, @Param("password") String password);

    @Select("select * from user where `index` = ${index}")
    User selectByIndex(@Param("index") Long index);
}
