package com.backend.vse.mapper;

import com.backend.vse.entity.Score;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
    @Select("SELECT * FROM score WHERE course_id=#{courseId}")
    List<Score> selectScoreListByCourseId(@Param("courseId") Long courseId);
}
