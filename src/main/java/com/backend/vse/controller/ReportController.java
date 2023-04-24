package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentSubmitDto;
import com.backend.vse.entity.ExperimentReview;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.service.OssService;
import com.backend.vse.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = {"Report"})
@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @Autowired
    OssService ossService;


    @ApiOperation("学生提交一份报告")
    @PostMapping(value = "submit", consumes = {MediaType.ALL_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<ExperimentSubmit> studentSubmitReport(
            @RequestPart("course_id") String cId,
            @RequestPart("experiment_id") String eId,
            @RequestPart("submit_time") String sTime,
            @RequestPart(value = "report") MultipartFile report,
            HttpServletRequest request
    ) {
        long courseId, experimentId;
        Timestamp submitTime = null;
        try {
            courseId = Long.parseUnsignedLong(cId);
            experimentId = Long.parseUnsignedLong(eId);
            submitTime = Timestamp.valueOf(sTime);
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }

        String reportUrl = ossService.uploadFile(report);
        if (reportUrl == null) {
            return Result.fail(400, "文件存储系统异常");
        }


        Long userId = JwtInterceptor.getLoginUser();
        if (userId == null) {
            try {
                String student_id = String.valueOf(request.getParameter("student_id"));
                userId = Long.parseUnsignedLong(student_id);
            } catch (Exception e) {
                return Result.fail(400, "缺失用户ID");
            }
        }

        ExperimentSubmit submit = new ExperimentSubmit();
        submit.setIndex(userId);
        submit.setCourseId(courseId);
        submit.setExperimentId(experimentId);
        submit.setContent(reportUrl);
        submit.setTime(submitTime);

        try {
            ExperimentSubmit result = reportService.submitReport(submit);
            if (result == null) {
                return Result.fail(2000, "不在可提交时间");
            } else {
                return Result.success(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(400, "提交失败");
        }
    }

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
    @GetMapping("getStudentSubmit")
    public Result<ExperimentSubmitDto> teacherReview(@ApiParam(name = "index", value = "提交者序号", required = true)
                                                     @RequestParam("index") Long index,
                                                     @ApiParam(name = "reportId", value = "实验报告ID", required = true)
                                                     @RequestParam("reportId") Long reportId) {
        ExperimentSubmitDto experimentSubmitDto = reportService.selectByIndexAndExperimentId(index, reportId);
        return Result.success(experimentSubmitDto);
    }
}
