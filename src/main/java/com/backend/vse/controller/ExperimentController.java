package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.service.ExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Experiment"})
@RestController
@RequestMapping("user")
public class ExperimentController {
    @Autowired
    private ExperimentService experimentService;

    @ApiOperation("根据课程id，返回该课程的所有实验信息")
    @GetMapping("getExperimentsByCourseId")
    public Result<List<ExperimentDto>> getExperimentsByCourseId(@ApiParam(name="courseId", value="课程id", required = true)
                                             @RequestParam("courseId") Long courseId)
    {
        List<ExperimentDto> experimentDtoList = experimentService.selectExperimentByCourseId(courseId);
        return Result.success(experimentDtoList);
    }


}
