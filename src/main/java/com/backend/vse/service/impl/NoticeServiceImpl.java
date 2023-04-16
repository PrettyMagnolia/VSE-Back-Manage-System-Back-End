package com.backend.vse.service.impl;

import com.backend.vse.dto.NoticeDto;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.entity.Notice;
import com.backend.vse.mapper.NoticeMapper;
import com.backend.vse.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;
    @Override
    public ArrayList<NoticeDto> selectByCourseId(Long courseId) {
        ArrayList<NoticeDto> result = new ArrayList<>();
        result= noticeMapper.selectByCourseId(courseId);
        return result;
    }

    @Override
    public Notice addNotice(Notice notice) {
        Notice result=new Notice(notice.getCourseId(), notice.getTitle(), notice.getContent(), notice.getTime());
        if(notice.getNoticeId()==null){
            noticeMapper.newNoticeInsert(notice.getCourseId(),notice.getTitle(),notice.getContent(),notice.getTime());
            Long noticeId = noticeMapper.getLastNoticeId();
            result.setNoticeId(noticeId);
        }else{
            noticeMapper.updateNotice(notice.getNoticeId(),notice.getTime(),notice.getContent(),notice.getTitle());
            result.setNoticeId(notice.getNoticeId());
        }
        return result;
    }
}
