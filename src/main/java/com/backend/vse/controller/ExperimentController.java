package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentBriefInfo;
import com.backend.vse.dto.ExperimentContentDto;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.ExperimentTemplateDto;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.Experiment;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.service.ExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    @GetMapping("experimentincourse")
    public Result<List<ExperimentDto>> getExperimentsByCourseId(@ApiParam(name = "courseId", value = "课程id", required = true)
                                                                @RequestParam("courseId") Long courseId) {
        List<ExperimentDto> experimentDtoList = experimentService.selectExperimentByCourseId(courseId);
        return Result.success(experimentDtoList);
    }

    @ApiOperation("根据课程id，返回该课程不包含实验信息")
    @GetMapping("experimentnotincourse")
    public Result<List<Experiment>> getExceptExperimentsByCourseId(@ApiParam(name = "courseId", value = "课程id", required = true)
                                                                      @RequestParam("courseId") Long courseId) {
        List<Experiment> experimentList = experimentService.selectExceptExperimentByCourseId(courseId);
        return Result.success(experimentList);
    }

    @ApiOperation("根据实验id，获取单个实验信息")
    @GetMapping("experiment/{experimentId}")
    public Result<ExperimentContentDto> getExperimentContentById(@ApiParam(name = "experimentId", value = "实验id", required = true)
                                                                 @PathVariable("experimentId") Long experimentId) {
        ExperimentContentDto experimentContentDto = experimentService.selectExperimentContentById(experimentId);
        return Result.success(experimentContentDto);
    }

    @ApiOperation("根据实验id，获取实验报告模板")
    @GetMapping("experiment_template/{experimentId}")
    public Result<ExperimentTemplateDto> getExperimentTemplateById(@ApiParam(name = "experimentId", value = "实验id", required = true)
                                                                   @PathVariable("experimentId") Long experimentId) {
        ExperimentTemplateDto experimentTemplateDto = experimentService.selectExperimentTemplateById(experimentId);
        return Result.success(experimentTemplateDto);
    }

    @ApiOperation("根据课程id和实验id，修改课程中的实验")
    @PutMapping("modify_experimentincourse")
    public Result<String> modifyExperimentInCourse(@RequestBody HashMap<String, String> map) throws ParseException {
        Long courseId = Long.valueOf(map.get("courseId"));
        Long experimentId = Long.valueOf(map.get("experimentId"));
        String startTime = map.get("startTime");
        String endTime = map.get("endTime");
        float score = Float.parseFloat(map.get("score"));
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        CourseExperiment courseExperiment = new CourseExperiment(courseId, experimentId, ft.parse(startTime), ft.parse(endTime), score);
        int res = experimentService.modifyExperimentInCourse(courseExperiment);
        if (res == 0) return Result.fail(400, "修改失败");
        else return Result.success("修改成功");
    }

    @ApiOperation("根据课程id和实验id，删除课程中的实验")
    @DeleteMapping("delete_experimentincourse")
    public Result<String> deleteExperimentInCourse(@ApiParam(name = "courseId", value = "课程id", required = true)
                                                   @RequestParam("courseId") Long courseId,
                                                   @ApiParam(name = "experimentId", value = "实验id", required = true)
                                                   @RequestParam("experimentId") Long experimentId) {
        int res = experimentService.deleteExperimentInCourse(courseId, experimentId);
        if (res == 0) return Result.fail(400, "删除失败");
        else return Result.success("删除成功");
    }

    @ApiOperation("根据课程id和实验id，添加课程中的实验")
    @PostMapping("add_experimentincourse")
    public Result<String> deleteExperimentInCourse(@RequestBody HashMap<String, Object> map) {
        String courseId = (String) map.get("courseId");
        ArrayList<String> experimentIdList = (ArrayList<String>) map.get("experimentIdList");
        for (String experimentId: experimentIdList) {
            int res = experimentService.insertExperimentInCourse(Long.valueOf(courseId), Long.valueOf(experimentId));
            if (res == 0) return Result.fail(400, "插入失败");
        }
        return Result.success("插入成功");
    }
}
