package com.backend.vse.service;

import com.backend.vse.dto.StudentMenuDto;
import com.backend.vse.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 赵帅涛
 * @date 2023/04/09
 * @since jdk17
 */
@Service
public interface MenuService {
    // 获取课程菜单项
    ArrayList<HashMap<String, Object>> getCourseMenuList(long teacherId);

    ArrayList<HashMap<String, Object>> getCourseSubmenu(long courseId);

    //设置菜单项属性
    HashMap<String, Object> getMenuMeta(String icon, String title);

    ArrayList<StudentMenuDto> buildMenuForStudent(User user);

    ArrayList<StudentMenuDto> buildWholeMenu();
}
