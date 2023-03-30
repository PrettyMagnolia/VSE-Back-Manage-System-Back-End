package com.backend.vse.controller;

import com.backend.vse.entity.User;
import com.backend.vse.common.Result;
import com.backend.vse.interceptor.util.JwtUtil;
import com.backend.vse.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/3/30
 * @JDKVersion 17.0.4
 */
@Api(tags = {"User"})
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("根据用户的id和学校返回指定用户（登录）")
    @PostMapping("login")
    public Result<String> login(@RequestParam("id") Long id, @RequestParam("school") String school) {
        User user = userService.findUser(id, school);
        if (user==null){
            return Result.fail(10001,"用户不存在");
        }
        //todo:判断密码错误

        String userId=user.getId().toString();
        String token = JwtUtil.sign(userId);
        return Result.success(token);
    }
}
