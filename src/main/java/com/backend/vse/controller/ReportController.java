package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.entity.ExperimentReview;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Api(tags = {"Report"})
@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @ApiOperation("学生提交一份报告")
    @PostMapping("studentSubmit")
    public void studentSubmit(@ApiParam(name="id", value="学生学号", required = true)
                                  @RequestParam("id") Long id,
                              @ApiParam(name="experimentId", value="实验ID", required = true)
                                  @RequestParam("experimentId") Long experimentId,
                              @ApiParam(name="courseId", value="课程ID", required = true)
                                  @RequestParam("courseId") Long courseId,
                              @ApiParam(name="content", value="实验报告的URL", required = true)
                                  @RequestParam("content") String content)
    {
        ExperimentSubmit experimentSubmit = new ExperimentSubmit();

        experimentSubmit.setReportId(-1L);
        experimentSubmit.setId(id);
        experimentSubmit.setExperimentId(experimentId);
        experimentSubmit.setCourseId(courseId);
        experimentSubmit.setContent(content);
        experimentSubmit.setTime(new Date(System.currentTimeMillis()));

        reportService.insertOneSubmit(experimentSubmit);
    }

    @ApiOperation("教师提交一份批改")
    @PostMapping("teacherReview")
    public void teacherReview(@ApiParam(name="reportId", value="实验报告ID", required = true)
                                  @RequestParam("reportId") Long reportId,
                              @ApiParam(name="id", value="批改者工号", required = true)
                                  @RequestParam("id") Long id,
                              @ApiParam(name="role", value="批改者身份，默认0为教师", required = true)
                                  @RequestParam("role") Byte role,
                              @ApiParam(name="score", value="批改分数", required = true)
                                  @RequestParam("score") float score)
    {
        ExperimentReview experimentReview = new ExperimentReview();

        experimentReview.setReportId(reportId);
        experimentReview.setId(id);
        experimentReview.setRole(role);
        experimentReview.setScore(score);
        experimentReview.setTime(new Date(System.currentTimeMillis()));

        reportService.insertOneReview(experimentReview);
    }
}
