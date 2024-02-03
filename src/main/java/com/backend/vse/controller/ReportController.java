package com.backend.vse.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.SelectObjectRequest;
import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentSubmitDto;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.entity.StudentAttendCourse;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.service.CourseService;
import com.backend.vse.service.OssService;
import com.backend.vse.service.ReportService;
//import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;

import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.*;

import java.util.Date;


import com.aliyun.oss.OSS;

@Api(tags = {"Report"})
@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @Autowired
    OssService ossService;

    @Autowired
    CourseService courseService;
//
//    @ApiOperation("学生提交一份报告")
//    @PostMapping(value = "submit", consumes = {MediaType.ALL_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public Result<ExperimentSubmit> studentSubmitReport(
//            @RequestPart("experiment_id") String eId,
//            @RequestPart("submit_time") String sTime,
//            @RequestPart(value = "report") MultipartFile report,
//            HttpServletRequest request
//    ) {
//        long courseId, experimentId;
//        Timestamp submitTime = null;
//        try {
//            experimentId = Long.parseUnsignedLong(eId);
//            submitTime = Timestamp.valueOf(sTime);
//        } catch (Exception e) {
//            return Result.fail(400, "参数解析错误");
//        }
//
//        String reportUrl = ossService.uploadFile(report);
//        if (reportUrl == null) {
//            return Result.fail(400, "文件存储系统异常");
//        }
//
//        Long userId = JwtInterceptor.getLoginUser();
//        if (userId == null) {
//            try {
//                String student_id = String.valueOf(request.getParameter("student_id"));
//                userId = Long.parseUnsignedLong(student_id);
//            } catch (Exception e) {
//                return Result.fail(400, "缺失用户ID");
//            }
//        }
////        userId=100l;
//        StudentAttendCourse studentAttendCourse = courseService.getCourseByIndex(userId);
//        courseId = studentAttendCourse.getCourseId();
////        System.out.print(courseId+"\n");
//
//        ExperimentSubmit submit = new ExperimentSubmit();
//        submit.setIndex(userId);
//        submit.setCourseId(courseId);
//        submit.setExperimentId(experimentId);
//        submit.setContent(reportUrl);
//        submit.setTime(submitTime);
//
//        try {
//            ExperimentSubmit result = reportService.submitReport(submit);
//            if (result == null) {
//                return Result.fail(2000, "不在可提交时间");
//            } else {
//                return Result.success(result);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.fail(400, "提交失败");
//        }
//    }

    @ApiOperation("根据提交者学号和实验ID，获取该学生提交的实验报告")
    @GetMapping("getByInfo")
    public Result<String> getReportByInfo(
            @RequestParam("experiment_id") String eId,
            @RequestParam("student_id") String sId,
            @RequestParam("name") String sName
    ) {
        // 获取环境变量，写入配置信息
        String reportFilename = eId + "-" + sName + "-" + sId + ".docx";
        String endpoint = "oss-cn-shanghai.aliyuncs.com";
        //从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        String bucketName = "vse1";
        String accessKeyId = "LTAI5tMGRu9r799jsSPzZQCW";
        String accessKeySecret = "bieHLw8OxSNB0DHCLgRPTslZH4NsoM";
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 先查看文件是否存在
        // 若不存在
        try {
            // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
            // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
            ossClient.getObject(new GetObjectRequest(bucketName, reportFilename));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            return Result.fail(404, "未找到文件");
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            return Result.fail(404, "未找到文件");
        }
        // 若存在
        URL signedUrl = null;
        try {
            // 指定生成的签名URL过期时间，单位为毫秒。本示例以设置过期时间为1小时为例。
            Date expiration = new Date(new Date().getTime() + 3600 * 1000L);

            // 生成签名URL。
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, reportFilename, HttpMethod.GET);

            // 设置过期时间。
            request.setExpiration(expiration);

            // 通过HTTP GET请求生成签名URL。
            signedUrl = ossClient.generatePresignedUrl(request);
            // 打印签名URL。
            System.out.println("signed url for getObject: " + signedUrl);
            return Result.success(signedUrl.toString());
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            return Result.fail(404, "授权异常");
        }
    }

    @ApiOperation("学生提交实验数据")
    @PostMapping(value = "submitData")
    public Result<Object> studentSubmitData(
            @RequestBody JSONObject dynamicJson
    ) {
        Process proc;
        String currentWorkingDirectory = System.getProperty("user.dir");
        String pythonScriptPath = currentWorkingDirectory + "/src/main/resources/" + "fillExpTemplate.py";
        // 若当前环境为生产环境
        if ("production".equals(System.getProperty("env"))) {
            pythonScriptPath = currentWorkingDirectory + "/fillExpTemplate.py";
        }
//        System.out.println(pythonScriptPath);
//        System.out.println(currentWorkingDirectory);
        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonString = objectMapper.writeValueAsString(dynamicJson);
//            System.out.println("jsonString: "+jsonString);
            String jsonString = dynamicJson.toString();
            StringBuilder result = new StringBuilder();
//            System.out.println("jsonString: "+jsonString);
            // 转义
            for (int i = 0; i < jsonString.length(); i++) {
                char currentChar = jsonString.charAt(i);
                result.append(currentChar);

                // 如果当前字符是双引号，前方插入\
                if (currentChar == '"' && i > 0 && i < jsonString.length() - 1) {
                    result.insert(result.length() - 1, '\\');
                }
            }
//            System.out.println("result:\n" + result);
//            System.out.println("现在执行:\n" + "python " + pythonScriptPath + " " + "\"" + result + "\"");
            if ("production".equals(System.getProperty("env"))) {
                proc = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "python " + pythonScriptPath + " " + "\"" + result + "\""});
            } else {
                proc = Runtime.getRuntime().exec("python " + pythonScriptPath + " " + "\"" + result + "\"");
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            String returnMsg = "";
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                returnMsg += line;
            }
            System.out.println("returnMsg:\n" + returnMsg);
            in.close();
            proc.waitFor();
            return Result.success(returnMsg);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail(400, e.getCause().toString());

        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.fail(401, e.getCause().toString());

        }
//        String currentWorkingDirectory = System.getProperty("user.dir");
//        String pythonScriptPath = currentWorkingDirectory+"\\"+"fillExpTemplate.py";
//        List<String> scriptArguments = Arrays.asList(dynamicJson.toString());
//
//        int exitCode = PythonRunner.runScript(pythonScriptPath, scriptArguments);
//
//        System.out.println("Python脚本执行完毕，退出码：" + exitCode);

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
