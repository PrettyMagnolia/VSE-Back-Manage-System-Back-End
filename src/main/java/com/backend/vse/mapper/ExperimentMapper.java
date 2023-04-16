package com.backend.vse.mapper;

import com.backend.vse.dto.ExperimentBriefInfo;
import com.backend.vse.dto.ExperimentScoreDto;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.Experiment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.output.ScoredValueListOutput;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author 2050865 黄彦铭
 * @Date 2023/4/8
 * @JDKVersion 17.0.4
 */
@Mapper
public interface ExperimentMapper extends BaseMapper<Experiment> {

    @Select("SELECT * FROM experiment")
    List<Experiment> selectAllExperiments();

    @Select("SELECT * FROM experiment WHERE experiment_id=#{experimentId}")
    Experiment selectExperimentById(@Param("experimentId") Long experimentId);

    @Select("SELECT * FROM course_experiment WHERE course_id=${courseId}")
    List<CourseExperiment> selectExperimentsByCourseId(@Param("courseId") Long courseId);

    @Select("SELECT experiment_name,score,content FROM experiment_submit JOIN experiment USING(experiment_id) " +
            "JOIN experiment_review USING(report_id) " +
            "WHERE experiment_submit.index=#{index} AND experiment.course_id=#{courseId}")
    @Results({
            @Result(column = "experiment_name", property = "experimentName"),
            @Result(column = "score", property = "score"),
            @Result(column = "content", property = "report")
    })
    List<ExperimentScoreDto> selectExperimentScoreByIndexAndCourseId(@Param("index") Long index,
                                                                     @Param("courseId") Long courseId);

    @Update("UPDATE course_experiment SET start_time=#{courseExperiment.startTime}, end_time=#{courseExperiment.endTime}, score=#{courseExperiment.score} WHERE course_id=#{courseExperiment.courseId} AND experiment_id=${courseExperiment.experimentId}")
    int updateExperimentInCourse(@Param("courseExperiment") CourseExperiment courseExperiment);

    @Delete("DELETE FROM course_experiment WHERE course_id=${courseId} AND experiment_id=${experimentId}")
    int deleteExperimentInCourse(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId);
}
