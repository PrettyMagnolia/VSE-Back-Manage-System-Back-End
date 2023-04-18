package com.backend.vse.mapper;

import com.backend.vse.dto.Account;
import com.backend.vse.dto.CourseExperimentDto;
import com.backend.vse.dto.StudentSimpleDto;
import com.backend.vse.entity.Course;
import com.backend.vse.entity.ExperimentReport;
import com.backend.vse.entity.ExperimentReview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface ReviewMapper extends BaseMapper<ExperimentReview> {

    @Select("SELECT user.`index` stuId, user.id schoolNumber, user.name stuName, user.school " +
            "FROM user, student_attend_course sac " +
            "WHERE user.role = 0 AND sac.`index` = user.`index` AND sac.course_id = #{courseId};")
    ArrayList<StudentSimpleDto> getCourseStudents(@Param("courseId") Long courseId);

    @Select("SELECT es.report_id, " +
            "       es.`index` stuId, " +
            "       es.experiment_id, " +
            "       es.course_id, " +
            "       es.content reportUrl, " +
            "       es.time    submitTime, " +
            "       er.time    reviewTime, " +
            "       er.score " +
            "FROM experiment_submit es LEFT JOIN experiment_review er ON es.report_id = er.report_id " +
            " WHERE es.course_id = #{courseId} AND es.experiment_id = #{experimentId}")
    ArrayList<ExperimentReport> getStudentSubmitList(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId);

    @Select("SELECT e.experiment_name, ce.experiment_id, ce.start_time, ce.end_time, ce.score " +
            "from experiment e,course_experiment ce " +
            "WHERE e.experiment_id = ce.experiment_id AND ce.course_id = #{courseId};")
    ArrayList<CourseExperimentDto> getCourseExperimentList(@Param("courseId") Long courseId);

    @Insert("INSERT INTO experiment_review (report_id, `index`, role, time, score) " +
            "VALUES (#{reportId}, #{reviewerId}, #{role}, current_timestamp, #{score}) " +
            "ON DUPLICATE KEY UPDATE `index` = #{reviewerId}, " +
            "                        role    = #{role}, " +
            "                        time    = current_timestamp, " +
            "                        score   = #{score} ")
    Boolean setReportScore(@Param("reportId") Long reportId, @Param("reviewerId") Long reviewerId, @Param("role") Boolean reviewRole, @Param("score") Float score);

    @Select("SELECT * FROM course WHERE course_id = #{id}")
    Course getCourseInfo(@Param("id") Long courseId);

    @Select("SELECT name, age, gender, email, avatar " +
            "FROM user, student_attend_course s " +
            "WHERE user.`index` = s.`index` AND s.course_id = #{courseId};")
    ArrayList<Account> getStudentList(@Param("courseId") Long courseId);

    @Select("SELECT name FROM vse.t_all_school WHERE LOCATE(#{key}, name) > 0")
    ArrayList<String> searchSchoolNameList(@Param("key") String key);
}
