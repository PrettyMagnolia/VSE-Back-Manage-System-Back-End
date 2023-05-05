package com.backend.vse.mapper;

import com.backend.vse.entity.CourseExperiment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

@Mapper
public interface CourseExperimentMapper extends BaseMapper<CourseExperiment> {
    @Select("SELECT * FROM course_experiment " +
            "WHERE course_id = #{courseId} AND experiment_id = #{experimentId} LIMIT 1")
    CourseExperiment getOneCourseExperiment(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId);


}
