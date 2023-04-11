package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.StudentCourseInfoDto;
import com.backend.vse.dto.StudentScoreDto;
import com.backend.vse.service.ScoreService;
import com.backend.vse.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Score"})
@RestController
//@RequestMapping("score")
public class ScoreController {
    @Autowired
    ScoreService scoreService;
    @Autowired
    UserService userService;

    @ApiOperation("根据课程id，获取该课程所有学生的成绩")
    @GetMapping("coursescore")
    public Result<List<StudentScoreDto>> getScoreListByCourseId(@ApiParam(name="courseId", value="课程ID", required = true)
                                                     @RequestParam("courseId") Long courseId)

    {
        List<StudentScoreDto> studentScoreDtoList = scoreService.selectScoreListByCourseId(courseId);
        return Result.success(studentScoreDtoList);
    }

    @ApiOperation("根据学生index与课程id，返回该学生的基本个人信息，以及在该课程中的所有实验分数")
    @GetMapping("studentscore")
    public Result<StudentCourseInfoDto> getStudentCourseInfo(@ApiParam(name="index", value="学生序号", required = true)
                                                                 @RequestParam("index") Long index,
                                                             @ApiParam(name="courseId", value="课程ID", required = true)
                                                                @RequestParam("courseId") Long courseId) {
        StudentCourseInfoDto studentCourseInfoDto = userService.selectStudentCourseInfoByIndexAndCourseId(index,courseId);
        return Result.success(studentCourseInfoDto);
    }
}
