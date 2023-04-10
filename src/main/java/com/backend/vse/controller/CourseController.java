package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = {"Course"})
@RestController
@RequestMapping("course")
@Transactional
public class CourseController {
    @Autowired
    CourseService courseService;

    @ApiOperation("新增一门课程")
    @PostMapping("postOneCourse")
    public Result<String> postOneCourse(@ApiParam(name="courseName", value="课程名", required = true)
                                @RequestParam("courseName") String courseName,
                              @ApiParam(name="semester", value="开课学期，可填spring或fall", required = true)
                                @RequestParam("semester") String semester,
                              @ApiParam(name="year", value="开课年份", required = true)
                                @RequestParam("year") int year,
                              @ApiParam(name="teacherList", value="授课教师列表，每一项包含index", required = true)
                                @RequestParam("teacherList") List<Long> teacherList,
                              @ApiParam(name="studentList", value="学生列表，每一项为index", required = true)
                                @RequestParam("studentList") List<Long> studentList)
    {
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
}
