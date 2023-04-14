package com.backend.vse.service.impl;

import com.backend.vse.dto.CourseExperimentDto;
import com.backend.vse.dto.StudentSimpleDto;
import com.backend.vse.dto.StudentSubmitDto;
import com.backend.vse.entity.ExperimentReport;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.ReviewMapper;
import com.backend.vse.service.ReviewService;
import com.backend.vse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author 赵帅涛
 * @date 2023/04/14
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    UserService userService;

    @Override
    public ArrayList<StudentSubmitDto> getStudentSubmitLogList(Long courseId, Long experimentId) {
        // 获取学生列表
        ArrayList<StudentSimpleDto> studentList = reviewMapper.getCourseStudents(courseId);
        studentList.sort(Comparator.comparing(StudentSimpleDto::getStuId));

        // 获取提交记录
        ArrayList<ExperimentReport> submitList = reviewMapper.getStudentSubmitList(courseId, experimentId);
        submitList.sort(Comparator.comparing(ExperimentReport::getStuId));

        ArrayList<StudentSubmitDto> result = new ArrayList<>();
        int lastSubmitIndex = 0;
        for (int i = 0; i < studentList.size(); i++) {
            StudentSubmitDto temp = new StudentSubmitDto();

            StudentSimpleDto student = studentList.get(i);
            temp.setStuId(student.getStuId());
            temp.setStuName(student.getStuName());
            temp.setSchoolNumber(student.getSchoolNumber());

            for (int j = lastSubmitIndex; j <= i && j < submitList.size(); j++) {
                ExperimentReport submit = submitList.get(j);
                if (student.getStuId().equals(submit.getStuId())) {
                    temp.setReportId(submit.getReportId());
                    temp.setReportUrl(submit.getReportUrl());
                    temp.setSubmitTime(submit.getSubmitTime());
                    temp.setScore(submit.getScore());
                    temp.setReviewTime(submit.getReviewTime());
                    lastSubmitIndex = j + 1;
                    break;
                }
            }
            result.add(temp);
        }
        return result;
    }

    @Override
    public boolean giveScoreOfReport(Long reportId, Float score, Long reviewerId, Boolean reviewerRole) {
        User currentUser = userService.findUserByIndex(reviewerId);
        if ((currentUser.getRole() == 1) == reviewerRole)
            return reviewMapper.setReportScore(reportId, reviewerId, reviewerRole, score);
        else return false;
    }

    @Override
    public ArrayList<CourseExperimentDto> getCourseExperimentList(Long experimentId) {
        return reviewMapper.getCourseExperimentList(experimentId);
    }
}
