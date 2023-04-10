package com.backend.vse.controller;

import com.backend.vse.dto.StudentCourseInfoDto;
import com.backend.vse.entity.User;
import com.backend.vse.common.Result;
import com.backend.vse.interceptor.util.JwtUtil;
import com.backend.vse.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/3/30
 * @JDKVersion 17.0.4
 */
@Api(tags = {"User"})
@RestController
//@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("根据用户的id和学校返回指定用户（登录）")
    @PostMapping("login")
    public Result<Map<String, String>> login(@RequestBody HashMap<String, String> map) {
        User user = userService.findUser(Long.parseLong(map.get("id")), map.get("school"));
        if (user == null) {
            return Result.fail(10001, "用户不存在");
        }
        //todo:判断密码错误

        String userId = user.getId().toString();
        String token = JwtUtil.sign(userId);
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("token", token);
        return Result.success(hashMap);
    }

    @ApiOperation("根据学生index与课程id，返回该学生的基本个人信息，以及在该课程中的所有实验分数")
    @PostMapping("user/getStudentCourseInfo")
    public Result<StudentCourseInfoDto> getStudentCourseInfo(Long index, Long courseId) {
        StudentCourseInfoDto studentCourseInfoDto = userService.selectStudentCourseInfoByIndexAndCourseId(index,courseId);
        return Result.success(studentCourseInfoDto);
    }
}
