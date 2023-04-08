package com.backend.vse.mapper;

import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.entity.Experiment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @Author 2050865 黄彦铭
 * @Date 2023/4/8
 * @JDKVersion 17.0.4
 */
@Mapper
public interface ExperimentMapper {
    @Select("SELECT * FROM experiment WHERE course_id=${courseId}")
    List<Experiment> selectExperimentsByCourseId(@Param("courseId") Long courseId);

//    @Mappings({
//            @Mapping(target = "experimentId", source = "experimentId"),
//            @Mapping(target = "courseId", source = "courseId"),
//            @Mapping(target = "weight", source = "weight"),
//            @Mapping(target = "startTime", source = "startTime"),
//            @Mapping(target = "endTime", source = "endTime")
//    })
//    List<ExperimentDto> experimentToDto(List<Experiment> experimentList);
}
