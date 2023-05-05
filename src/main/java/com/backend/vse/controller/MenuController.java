package com.backend.vse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.StudentMenuDto;
import com.backend.vse.entity.User;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.service.MenuService;
import com.backend.vse.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 赵帅涛
 * @date 2023/04/09
 * @since jdk17
 */
@Api(tags = "Menu")
@RestController()
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;


    @GetMapping("list")
    public Result<JSONArray> getUserMenu() {
        // 获取UserId
        Long userId = JwtInterceptor.getLoginUser();
        if (userId == null) {
//            return Result.fail(4001, "用户未登录或会话过期");
            userId = 199L;
        }
        User currentUser = userService.findUserByIndex(userId);
        if (currentUser == null) {
            return Result.fail(10001, "用户不存在");
        }

        JSONArray result = new JSONArray();
        if (currentUser.getRole() == 1) {
            // 确认是教师
            String json;
            try {
                ClassPathResource resource = new ClassPathResource("json/routes.json");
                byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
                json = new String(bytes, StandardCharsets.UTF_8);
            } catch (IOException e) {
                return Result.fail(5001, "文件读取失败");
            }

            result = JSON.parseObject(json, JSONArray.class);
            result.remove(1);

            ArrayList<HashMap<String, Object>> children = menuService.getCourseMenuList(userId);
            if (children == null || children.size() == 0) {
                return Result.success(result);
            }

            HashMap<String, Object> menuItem = new HashMap<>();
            menuItem.put("path", "/teaching");
            menuItem.put("name", "teaching");
            menuItem.put("redirect", children.get(0).get("path"));
            menuItem.put("meta", menuService.getMenuMeta("DataLine", "我的授课"));
            menuItem.put("children", children);

            result.add(1, menuItem);
        } else {
            // 身份为助教等
        }
        return Result.success(result);
    }

    @GetMapping("student_experiment")
    public Result<ArrayList<StudentMenuDto>> getStudentMenuList() {
        Long index = JwtInterceptor.getLoginUser();
//        Long index= 1l;
        User user = userService.findUserByIndex(index);
        ArrayList<StudentMenuDto> Menu=menuService.buildMenuForStudent(user);
//        String json = null;
//        try {
//            ClassPathResource resource = new ClassPathResource("json/routes.json");
//            byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
//            json = new String(bytes, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            return Result.fail(5001, "文件读取失败");
//        }
//        JSONArray result = new JSONArray();
//        result = JSON.parseObject(json, JSONArray.class);
        return Result.success(Menu);
    }
}
