package com.backend.vse.controller;

import com.backend.vse.dto.Account;
import com.backend.vse.dto.UserInfo;
import com.backend.vse.entity.User;
import com.backend.vse.common.Result;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.interceptor.util.JwtUtil;
import com.backend.vse.service.UserService;
import com.backend.vse.tools.MailSender;
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
    public Result<Map<String, String>> login(@RequestBody HashMap<String, String> map) throws Exception {
        User user = userService.findUserByIdAndSchool(map.get("username"), map.get("school"), map.get("password"));
        if (user == null) {
            return Result.fail(10001, "用户不存在");
        }

        if (user.getStatus() == 1) { // 用户账号已激活，成功登录，返回token
            String userIndex = user.getIndex().toString();
            String token = JwtUtil.sign(userIndex);
            Map<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("token", token);
            return Result.success(hashMap);
        }
        else { // 用户账户未激活，激活流程
            // 发送邮箱验证码
            MailSender.sendEmail(user.getEmail());
            // todo 验证码存入redis 设置expire
            return Result.fail(400, "账户需要激活");
        }
    }

    @ApiOperation("获取用户信息（登录之后根据token）")
    @GetMapping("account")
    public Result<UserInfo> getUserInfo() {
        // 从拦截器中获取用户index
        Long index = JwtInterceptor.getLoginUser();
        User user = userService.findUserByIndex(index);
        if (user == null) {
            return Result.fail(10001, "用户不存在");
        }

        Account account = new Account(user.getName(), user.getAge(), user.getGender(), user.getEmail(), user.getAvatar());
        UserInfo userInfo = new UserInfo(account, new String[]{"edit", "delete", "add"}, user.getRole());

        return Result.success(userInfo);
    }

    @ApiOperation("用户账户激活")
    @PostMapping("activate")
    public void accountActivate() {
        // 传入验证码 新的密码
        // 验证验证码是否正确
    }
}
