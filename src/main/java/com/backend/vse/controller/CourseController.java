package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.CourseBasicInfoDto;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.ImportedStudentDto;
import com.backend.vse.dto.NewCourseDto;
import com.backend.vse.entity.Course;
import com.backend.vse.entity.StudentAttendCourse;
import com.backend.vse.entity.TeacherTeachCourse;
import com.backend.vse.entity.User;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.mapper.StudentAttendCourseMapper;
import com.backend.vse.mapper.UserMapper;
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
    @Autowired
    UserMapper userMapper;

    @ApiOperation("新增一门课程")
    @PostMapping("addcourse")
    public Result<String> postOneCourse(@RequestBody NewCourseDto newCourseDto)
    {
        Long teacherIndex = JwtInterceptor.getLoginUser();
        List<Long> teacherList = new ArrayList<>();
        teacherList.add(teacherIndex);
        newCourseDto.setTeacherList(teacherList);

        String courseName = newCourseDto.getCourseName();
        String semester = newCourseDto.getSemester();;
        List<ImportedStudentDto> studentList = newCourseDto.getStudentList();
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

        //2.补足学生用户表
        studentList.forEach(item -> {
            Long id = item.getId();
            String school = item.getSchool();
            User stu = userMapper.selectByStuIdAndSchool(id, school);
            //如果没有这个学生，就现加这个学生
            if(stu == null){
                int res = userMapper.insertUser(0L,id,item.getName(),"111111",0,
                        item.getGender(),item.getEmail(),school, (byte) 0, (byte) 1,
                        "https://pic1.zhimg.com/v2-c0649aa7bd799ee4beefa8098ca7cf16_r.jpg?source=1940ef5c");
                stu = userMapper.selectByStuIdAndSchool(id, school);
            }



        });

        try {
            //3.插入教师参与课程的表
            teacherList.forEach(index -> {
                if (courseService.insertOneTeach(new TeacherTeachCourse(index, courseId)) <= 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new DatabaseException("插入失败！");
                }
            });
            //4.插入学生参与课程的表
            studentList.forEach(item -> {
                //先查出index
                User stu = userMapper.selectByStuIdAndSchool(item.getId(), item.getSchool());
                Long index = stu.getIndex();

                //然后执行Insert
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
        index = JwtInterceptor.getLoginUser();

        List<CourseBasicInfoDto> courseBasicInfoDtoList = courseService.getCoursesByTeacher(index);
        //按时间降序输出，dto类已实现Comparable接口
        List<CourseBasicInfoDto> reverseList = courseBasicInfoDtoList.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        return Result.success(reverseList);
    }
}
