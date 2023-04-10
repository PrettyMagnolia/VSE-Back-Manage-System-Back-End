package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentSubmitDto;
import com.backend.vse.entity.ExperimentReview;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
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

//    @ApiOperation("学生提交一份报告")
//    @PostMapping("studentSubmit")
//    public Result<String> studentSubmit(@ApiParam(name="index", value="提交者序号", required = true)
//                                  @RequestParam("index") Long index,
//                              @ApiParam(name="experimentId", value="实验ID", required = true)
//                                  @RequestParam("experimentId") Long experimentId,
//                              @ApiParam(name="courseId", value="课程ID", required = true)
//                                  @RequestParam("courseId") Long courseId,
//                              @ApiParam(name="content", value="实验报告的URL", required = true)
//                                  @RequestParam("content") String content)
//    {
//        ExperimentSubmit experimentSubmit = new ExperimentSubmit();
//
//        experimentSubmit.setIndex(index);
//        experimentSubmit.setExperimentId(experimentId);
//        experimentSubmit.setCourseId(courseId);
//        experimentSubmit.setContent(content);
//        experimentSubmit.setTime(new Date(System.currentTimeMillis()));
//
//        if(reportService.insertOneSubmit(experimentSubmit) <= 0){
//            throw new DatabaseException("插入失败！");
//        }
//        return Result.success("提交报告成功！");
//    }
//
//    @ApiOperation("教师提交一份批改")
//    @PostMapping("teacherReview")
//    public Result<String> teacherReview(@ApiParam(name="reportId", value="实验报告ID", required = true)
//                                  @RequestParam("reportId") Long reportId,
//                              @ApiParam(name="index", value="批改者工号", required = true)
//                                  @RequestParam("index") Long index,
//                              @ApiParam(name="role", value="批改者身份，默认0为教师", required = true)
//                                  @RequestParam("role") Byte role,
//                              @ApiParam(name="score", value="批改分数", required = true)
//                                  @RequestParam("score") float score)
//    {
//        ExperimentReview experimentReview = new ExperimentReview();
//
//        experimentReview.setReportId(reportId);
//        experimentReview.setIndex(index);
//        experimentReview.setRole(role);
//        experimentReview.setScore(score);
//        experimentReview.setTime(new Date(System.currentTimeMillis()));
//
//        if(reportService.insertOneReview(experimentReview)<=0){
//            throw new DatabaseException("插入失败！");
//        }
//
//        return Result.success("批改更新成功！");
//    }

    @ApiOperation("根据提交者序号和实验ID，获取该学生提交的实验报告信息")
    @GetMapping ("getStudentSubmit")
    public Result<ExperimentSubmitDto> teacherReview(@ApiParam(name="index", value="提交者序号", required = true)
                                @RequestParam("index") Long index,
                                                     @ApiParam(name="reportId", value="实验报告ID", required = true)
                                @RequestParam("reportId") Long reportId)

    {
        ExperimentSubmitDto experimentSubmitDto = reportService.selectByIndexAndExperimentId(index, reportId);
        return Result.success(experimentSubmitDto);
    }
}
