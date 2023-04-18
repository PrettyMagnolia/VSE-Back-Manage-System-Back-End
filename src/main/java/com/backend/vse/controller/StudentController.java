package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.NoticeDto;
import com.backend.vse.dto.StudentSimpleDto;
import com.backend.vse.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Api(tags = {"courseStudent"})
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;


    @ApiOperation("根据课程ID，获取该课程的学生名单")
    @GetMapping("coursestudent")
    public Result<ArrayList<StudentSimpleDto>> GetCourseStudents(@ApiParam(name = "courseId", value = "课程序号", required = true)
                                                         @RequestParam("courseId") String cId) {
        long courseId;
        try {
            courseId = Long.parseUnsignedLong(cId);
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }
        ArrayList<StudentSimpleDto> studentList = studentService.selectByCourseId(courseId);
        return Result.success(studentList);
    }
}
