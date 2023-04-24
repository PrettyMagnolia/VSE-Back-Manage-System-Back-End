package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.NoticeDto;
import com.backend.vse.dto.StudentAttendCourseDto;
import com.backend.vse.dto.StudentInfoDto;
import com.backend.vse.dto.StudentSimpleDto;
import com.backend.vse.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Api(tags = {"courseStudent"})
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;


    @ApiOperation("根据课程ID，获取该课程的学生名单")
    @GetMapping("coursestudent")
    public Result<ArrayList<StudentInfoDto>> GetCourseStudents(@ApiParam(name = "courseId", value = "课程序号", required = true)
                                                         @RequestParam("courseId") String cId) {
        long courseId;
        try {
            courseId = Long.parseUnsignedLong(cId);
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }
        ArrayList<StudentInfoDto> studentList = studentService.selectByCourseId(courseId);
        return Result.success(studentList);
    }
    @ApiOperation("根据课程ID，获取该课程的可选课学生名单")
    @GetMapping("coursefreestudent")
    public Result<ArrayList<StudentInfoDto>> GetCourseFreeStudents(@ApiParam(name = "courseId", value = "课程序号", required = true)
                                                               @RequestParam("courseId") String cId) {
        long courseId;
        try {
            courseId = Long.parseUnsignedLong(cId);
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }
        ArrayList<StudentInfoDto> studentList = studentService.selectFreeByCourseId(courseId);
        return Result.success(studentList);
    }

    @ApiOperation("根据学生信息添加学生（非新增用户）")
    @PostMapping("coursestudentadd")
    public Result<String> CourseStudentadd(
            @ApiParam(name = "courseId", value = "课程序号", required = true)
            @RequestParam("courseId") String cId,
            @ApiParam(name = "stuId", value = "学号", required = true)
            @RequestParam("stuId") String sId,
            @ApiParam(name = "school", value = "学校", required = true)
            @RequestParam("school") String school,
            @ApiParam(name = "name", value = "姓名", required = true)
            @RequestParam("name") String name)
    {
        long courseId;
        try {
            courseId = Long.parseUnsignedLong(cId);
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }
        StudentAttendCourseDto existStudent=new StudentAttendCourseDto();
        existStudent.setCourseId(courseId);
        existStudent.setStuId(sId);
        existStudent.setName(name);
        existStudent.setSchool(school);
        String result = studentService.addExistStudent(existStudent);
        return Result.success(result);
    }

    @ApiOperation("根据学生信息删除学生（非新增用户）")
    @DeleteMapping("coursestudentdelete")
    public Result<String> CourseStudentDelete(
            @ApiParam(name = "courseId", value = "课程序号", required = true)
            @RequestParam("courseId") String cId,
            @ApiParam(name = "stuId", value = "学号", required = true)
            @RequestParam("stuId") String sId,
            @ApiParam(name = "school", value = "学校", required = true)
            @RequestParam("school") String school,
            @ApiParam(name = "name", value = "姓名", required = true)
            @RequestParam("name") String name)
    {
        long courseId;
        try {
            courseId = Long.parseUnsignedLong(cId);
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }
        StudentAttendCourseDto existStudent=new StudentAttendCourseDto();
        existStudent.setCourseId(courseId);
        existStudent.setStuId(sId);
        existStudent.setName(name);
        existStudent.setSchool(school);
        String result = studentService.deleteExistStudent(existStudent);
        return Result.success(result);
    }
}
