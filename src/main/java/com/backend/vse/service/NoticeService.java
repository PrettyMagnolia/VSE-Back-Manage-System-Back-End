package com.backend.vse.service;

import com.backend.vse.dto.NoticeDto;
import com.backend.vse.entity.Notice;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public interface NoticeService {
    ArrayList<NoticeDto> selectByCourseId(Long courseId);

    Notice addNotice(Notice notice);

    String deleteByNoticeId(Long noticeId);
}
