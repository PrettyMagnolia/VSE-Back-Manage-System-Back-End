package com.backend.vse.mapper;

import com.backend.vse.entity.ExperimentSubmit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;

@Mapper
public interface SubmitMapper extends BaseMapper<ExperimentSubmit> {
    @Select("SELECT * FROM experiment_submit WHERE `index`=#{index} AND experiment_id=#{experimentId}")
    ExperimentSubmit selectByIndexAndExperimentId(@Param("index") Long index, @Param("experimentId") Long experimentId);

    @Insert("INSERT INTO experiment_submit(`index`, experiment_id, course_id, content, time)" +
            "VALUES (#{index},#{experimentId},#{courseId},#{url},#{time})")
    boolean firstInsertSubmit(@Param("index") Long index, @Param("experimentId") Long experimentId,
                              @Param("courseId") Long courseId, @Param("url") String content, @Param("time") Timestamp submitTime);

    @Select("SELECT LAST_INSERT_ID() from experiment_submit LIMIT 1")
    Long getLastReportId();

    @Select("SELECT * FROM experiment_submit WHERE `index`=#{index} AND experiment_id=#{experimentId} AND course_id=#{courseId} LIMIT 1")
    ExperimentSubmit selectByIndexExpIdCourseId(@Param("index") Long index, @Param("experimentId") Long experimentId, @Param("courseId") Long courseId);

    @Update("UPDATE experiment_submit " +
            "SET content = #{content} , time = #{time} " +
            "WHERE report_id = #{report_id} ")
    void updateReport(@Param("report_id") Long reportId, @Param("time") Timestamp timeStamp, @Param("content") String url);
}
