package com.backend.vse.service.impl;

import com.backend.vse.dto.ExperimentScoreDto;
import com.backend.vse.dto.StudentCourseInfoDto;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/3/30
 * @JDKVersion 17.0.4
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ExperimentMapper experimentMapper;

    @Override
    public User findUser(Long id, String school) {
        User user = userMapper.selectByIDAndSchool(id, school);
        return user;
    }

    @Override
    public StudentCourseInfoDto selectStudentCourseInfoByIndexAndCourseId(Long index, Long courseId) {
        StudentCourseInfoDto studentCourseInfoDto = userMapper.selectStudentCourseInfoByIndexAndCourseId(index,courseId);
        List<ExperimentScoreDto> experimentScoreDtoList = experimentMapper.selectExperimentScoreByIndexAndCourseId(index,courseId);
        studentCourseInfoDto.setExperimentScoreList(experimentScoreDtoList);
        return studentCourseInfoDto;
    }
}
