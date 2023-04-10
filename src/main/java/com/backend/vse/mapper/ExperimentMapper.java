package com.backend.vse.mapper;

import com.backend.vse.dto.ExperimentScoreDto;
import com.backend.vse.entity.Experiment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author 2050865 黄彦铭
 * @Date 2023/4/8
 * @JDKVersion 17.0.4
 */
@Mapper
public interface ExperimentMapper extends BaseMapper<Experiment> {
    @Select("SELECT * FROM experiment WHERE course_id=${courseId}")
    List<Experiment> selectExperimentsByCourseId(@Param("courseId") Long courseId);

    @Select("SELECT experiment_id,score FROM experiment_submit JOIN experiment_review USING(report_id) " +
            "WHERE experiment_submit.index=#{index} AND course_id=#{courseId}")
    List<ExperimentScoreDto> selectExperimentScoreByIndexAndCourseId(@Param("index") Long index,
                                                                     @Param("courseId") Long courseId);
}
