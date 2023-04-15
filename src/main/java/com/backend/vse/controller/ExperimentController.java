package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentBriefInfo;
import com.backend.vse.dto.ExperimentContentDto;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.ExperimentTemplateDto;
import com.backend.vse.entity.Experiment;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.service.ExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Experiment"})
@RestController
//@RequestMapping("experiment")
public class ExperimentController {
    @Autowired
    private ExperimentService experimentService;

    @ApiOperation("获取所有实验信息")
    @GetMapping("allexperiment")
    public Result<List<Experiment>> getAllExperiments() {
        return Result.success(experimentService.selectAllExperiments());
    }

    @ApiOperation("根据课程id，返回该课程的所有实验信息")
    @GetMapping("expermentincourse")
    public Result<List<ExperimentDto>> getExperimentsByCourseId(@ApiParam(name="courseId", value="课程id", required = true)
                                             @RequestParam("courseId") Long courseId)
    {
        List<ExperimentDto> experimentDtoList = experimentService.selectExperimentByCourseId(courseId);
        return Result.success(experimentDtoList);
    }

    @ApiOperation("根据实验id，获取单个实验信息")
    @GetMapping("experiment/{experimentId}")
    public Result<ExperimentContentDto> getExperimentContentById(@ApiParam(name="experimentId", value="实验id", required = true)
                                            @PathVariable("experimentId") Long experimentId)
    {
        ExperimentContentDto experimentContentDto = experimentService.selectExperimentContentById(experimentId);
        return Result.success(experimentContentDto);
    }

    @ApiOperation("根据实验id，获取实验报告模板")
    @GetMapping("experiment_template/{experimentId}")
    public Result<ExperimentTemplateDto> getExperimentTemplateById(@ApiParam(name="experimentId", value="实验id", required = true)
                                                            @PathVariable("experimentId") Long experimentId)
    {
        ExperimentTemplateDto experimentTemplateDto = experimentService.selectExperimentTemplateById(experimentId);
        return Result.success(experimentTemplateDto);
    }


}
