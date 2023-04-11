package com.backend.vse.mapper;

import com.backend.vse.dto.StudentScoreDto;
import com.backend.vse.entity.Score;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
    @Select("SELECT * FROM score JOIN user USING(`index`) WHERE course_id=#{courseId}")
    @Results(
            {
                    //column为数据库字段名，property为实体类字段名
                    @Result(column = "id",property = "studentId"),
                    @Result(column = "name", property = "studentName"),
                    @Result(column = "score", property = "score")
            }
    )
    List<StudentScoreDto> selectScoreListByCourseId(@Param("courseId") Long courseId);
}
