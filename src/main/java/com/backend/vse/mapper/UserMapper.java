package com.backend.vse.mapper;

import com.backend.vse.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/3/30
 * @JDKVersion 17.0.4
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where id = '${id}' and school='${school}'")
    User selectByIDAndSchool(@Param("id") Long id, @Param("school") String school);

}
