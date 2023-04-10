package com.backend.vse.mapper;

import com.backend.vse.entity.ExperimentSubmit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SubmitMapper extends BaseMapper<ExperimentSubmit> {
    @Select("SELECT * FROM experiment_submit WHERE `index`=#{index} AND experiment_id=#{experimentId}")
    ExperimentSubmit selectByIndexAndExperimentId(@Param("index") Long index,@Param("experimentId") Long experimentId);
}
