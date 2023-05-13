package com.backend.vse.service;

import com.backend.vse.dto.StudentCourseInfoDto;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/3/30
 * @JDKVersion 17.0.4
 */
@Service
public interface UserService {
    StudentCourseInfoDto selectStudentCourseInfoByIndexAndCourseId(Long index, Long courseId);

    User findUserByIdAndSchool(String email, String school, String password);

    User findUserByIndex(Long index);

    Integer activateUserAccount(String email, String password, Byte status);

    void updateAvatar(String url, Long Id);

    Integer updatePassword(String email, String password);
}
