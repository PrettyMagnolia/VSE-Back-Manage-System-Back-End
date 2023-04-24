package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentSubmitDto;
import com.backend.vse.dto.NoticeDto;
import com.backend.vse.entity.ExperimentReview;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.entity.Notice;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.service.NoticeService;
import com.backend.vse.service.OssService;
import com.backend.vse.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Array;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = {"coursenotice"})
@RestController
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @Autowired
    OssService ossService;


    @ApiOperation("添加一个课程公告")
    @PostMapping("coursenoticeadd")
    public Result<Notice> CourseNoticeAdd(
            @RequestPart("noticeId") String nId,
            @RequestPart("courseId") String cId,
            @RequestPart("title") String title,
            @RequestPart("time") Timestamp time,
            @RequestPart(value = "content") MultipartFile content,
            HttpServletRequest request
    ) {
        long courseId,noticeId=0;
        try {
            courseId = Long.parseUnsignedLong(cId);
            if(nId!=null){
                noticeId = Long.parseUnsignedLong(nId);
            }
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }

        String contentUrl = ossService.uploadFile(content);
        Notice notice=new Notice();
        if(nId!=null) {
            notice.setNoticeId(noticeId);
        }else{
            notice.setNoticeId(null);
        }
        notice.setCourseId(courseId);
        notice.setTime(time);
        notice.setContent(contentUrl);
        notice.setTitle(title);
        try {
            Notice result = noticeService.addNotice(notice);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(400, "修改失败");
        }
    }

    @ApiOperation("根据课程ID，获取该课程的公告")
    @GetMapping("coursenotice")
    public Result<ArrayList<NoticeDto>> GetCourseNotices(@ApiParam(name = "courseId", value = "课程序号", required = true)
                                                     @RequestParam("courseId") Long courseId) {
        ArrayList<NoticeDto> NoticeDto = noticeService.selectByCourseId(courseId);
        return Result.success(NoticeDto);
    }
}
