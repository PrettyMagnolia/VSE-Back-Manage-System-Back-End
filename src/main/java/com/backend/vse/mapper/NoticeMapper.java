package com.backend.vse.mapper;

import com.backend.vse.dto.NoticeDto;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    @Select("SELECT * FROM notice WHERE course_id =#{courseId}")
    ArrayList<NoticeDto> selectByCourseId(@Param("courseId") Long courseId);

    @Insert("INSERT INTO notice(course_id, title, content, time)" +
            "VALUES (#{courseId},#{title},#{url},#{time})")
    boolean newNoticeInsert(@Param("courseId") Long courseId, @Param("title") String title,
                              @Param("url") String content, @Param("time") Timestamp time);

    @Update("UPDATE notice " +
            "SET title = #{title}, content = #{content} , time = #{time} " +
            "WHERE notice_id = #{notice_id} ")
    void updateNotice(@Param("notice_id") Long noticeId,
                      @Param("time") Timestamp time,
                      @Param("content") String url,
                      @Param("title") String title);

    @Select("SELECT * FROM notice WHERE notice_id=#{noticeId} LIMIT 1")
    Notice selectByNoticeId(@Param("noticeId") Long noticeId);

    @Select("SELECT LAST_INSERT_ID() from notice LIMIT 1")
    Long getLastNoticeId();
    @Delete("DELETE FROM notice WHERE notice_id = #{noticeId}")
    void deleteNotice(@Param("noticeId") Long noticeId);
}
