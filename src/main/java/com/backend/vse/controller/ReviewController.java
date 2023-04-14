package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.CourseExperimentDto;
import com.backend.vse.dto.GiveReportScoreDto;
import com.backend.vse.dto.StudentSubmitDto;
import com.backend.vse.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author 赵帅涛
 * @date 2023/04/14
 */
@Api(tags = "评阅实验报告")
@RestController
@RequestMapping("review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @ApiOperation("获取当前课程的所有实验")
    @GetMapping("experimentlist")
    public Result<ArrayList<CourseExperimentDto>> getCourseExperimentList(@RequestParam("courseId") String courseId) {
        long cId;
        try {
            cId = Long.parseUnsignedLong(courseId);
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }
        ArrayList<CourseExperimentDto> result;
        try {
            result = reviewService.getCourseExperimentList(cId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500, "数据库连接错误");
        }
        return Result.success(result);
    }

    @ApiOperation("获取当前实验的学生提交的实验报告")
    @GetMapping("reportlist")
    public Result<ArrayList<StudentSubmitDto>> getReportList(@RequestParam("courseId") String courseId, @RequestParam("experimentId") String experimentId) {
        long cId, eId;
        try {
            cId = Long.parseUnsignedLong(courseId);
            eId = Long.parseUnsignedLong(experimentId);
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }
        ArrayList<StudentSubmitDto> result;

        try {
            result = reviewService.getStudentSubmitLogList(cId, eId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500, "数据库连接错误");
        }
        return Result.success(result);
    }

    @ApiOperation("给某实验报告打分")
    @PostMapping("score")
    public Result<Boolean> giveScore(@RequestBody GiveReportScoreDto scoreDto) {
        boolean res = false;
        try {
            res = reviewService.giveScoreOfReport(scoreDto.getReportId(), scoreDto.getScore(), scoreDto.getReviewerId(), scoreDto.getReviewerRole());
        } catch (Exception e) {
            return Result.fail(500, "数据库连接错误");
        }
        if (!res) {
            return Result.fail(400, "无批阅权限");
        }
        return Result.success(res);
    }
}
