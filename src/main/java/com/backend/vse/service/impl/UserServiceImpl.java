package com.backend.vse.service.impl;

import com.backend.vse.common.Result;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/3/30
 * @JDKVersion 17.0.4
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByIdAndSchool(String email, String school, String password) {
        return userMapper.selectByIDAndSchool(email, school, password);
    }

    @Override
    public User findUserByIndex(Long index) {
        return userMapper.selectByIndex(index);
    }

    @Override
    public Integer activateUserAccount(String email, String password, Byte status) {
        return userMapper.updatePassword(email, password) & userMapper.updateStatus(email, status);
    }
}
