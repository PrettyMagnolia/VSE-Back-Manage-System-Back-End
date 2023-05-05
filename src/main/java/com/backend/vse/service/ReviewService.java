package com.backend.vse.service;

import com.backend.vse.dto.Account;
import com.backend.vse.dto.CourseExperimentDto;
import com.backend.vse.dto.StudentSubmitDto;
import com.backend.vse.entity.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author 赵帅涛
 * @date 2023/04/14
 */
@Service
public interface ReviewService {

    // 获取课程某实验学生提交的实验报告列表
    ArrayList<StudentSubmitDto> getStudentSubmitLogList(Long courseId, Long experimentId);

    // 给某实验报告打分
    boolean giveScoreOfReport(Long reportId, Float score, Long reviewerId, Boolean reviewerRole);

    // 获取当前课程的实验项目
    ArrayList<CourseExperimentDto> getCourseExperimentList(Long experimentId);

    Course getCourseInfo(Long courseId);

    ArrayList<Account> getStudentList(Long courseId);

    ArrayList<String> getSchoolNameList(String key);

    Boolean changeUserBaseInfo(Account account,Long userId);
}
