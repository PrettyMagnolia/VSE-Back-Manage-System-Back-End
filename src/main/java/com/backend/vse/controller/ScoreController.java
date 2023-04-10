package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentSubmitDto;
import com.backend.vse.dto.ScoreDto;
import com.backend.vse.mapper.ScoreMapper;
import com.backend.vse.service.ScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Score"})
@RestController
@RequestMapping("score")
public class ScoreController {
    @Autowired
    ScoreService scoreService;

    @ApiOperation("根据课程id，获取该课程所有学生的成绩")
    @GetMapping("getScoreListByCourseId")
    public Result<List<ScoreDto>> getScoreListByCourseId(@ApiParam(name="courseId", value="课程ID", required = true)
                                                     @RequestParam("courseId") Long courseId)

    {
        List<ScoreDto> scoreDtoList = scoreService.selectScoreListByCourseId(courseId);
        return Result.success(scoreDtoList);
    }
}
