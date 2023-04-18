package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.CourseBasicInfoDto;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.NewCourseDto;
import com.backend.vse.entity.Course;
import com.backend.vse.entity.StudentAttendCourse;
import com.backend.vse.entity.TeacherTeachCourse;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.StudentAttendCourseMapper;
import com.backend.vse.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(tags = {"Course"})
@RestController
//@RequestMapping("course")
@Transactional
public class CourseController {
    @Autowired
    CourseService courseService;

    @ApiOperation("新增一门课程")
    @PostMapping("addcourse")
    public Result<String> postOneCourse(@RequestBody NewCourseDto newCourseDto)
    {
        String courseName = newCourseDto.getCourseName();
        String semester = newCourseDto.getSemester();;
        List<Long> studentList = newCourseDto.getStudentList();
        List<Long> teacherList = newCourseDto.getTeacherList();
        int year = newCourseDto.getYear();
        //涉及到多个CRUD，注意事务回滚！
        //1.先插入课程表
        Course course = new Course(courseName, semester, year);
        if(courseService.insertOneCourse(course) <= 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(10001,"课程的courseName,semester,year中可能传错，请检查");
        }
        //拿到插入课程后，新生成的课程ID
        Long courseId = course.getCourseId();

        //2.再插入学生与教师参与课程的表
        try {
            teacherList.forEach(index -> {
                if (courseService.insertOneTeach(new TeacherTeachCourse(index, courseId)) <= 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new DatabaseException("插入失败！");
                }
            });
            studentList.forEach(index -> {
                if (courseService.insertOneAttend(new StudentAttendCourse(index, courseId)) <= 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new DatabaseException("插入失败！");
                }
            });
        }
        catch (DatabaseException e){
            return Result.fail(10001,"教师或学生列表可能传错，请检查");
        }

        return Result.success("新增课程成功！");
    }

    @ApiOperation("获取一名教师的所有课程")
    @GetMapping("courses")
    public Result<List<CourseBasicInfoDto>> postOneCourse(@ApiParam(name="index", value="教师index", required = true)
                                        @RequestParam("index") Long index)
    {
        List<CourseBasicInfoDto> courseBasicInfoDtoList = courseService.getCoursesByTeacher(index);
        //按时间降序输出，dto类已实现Comparable接口
        List<CourseBasicInfoDto> reverseList = courseBasicInfoDtoList.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        return Result.success(reverseList);
    }
}
