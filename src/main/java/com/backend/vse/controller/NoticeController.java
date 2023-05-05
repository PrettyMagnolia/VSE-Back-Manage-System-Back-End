package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.NoticeDto;
import com.backend.vse.entity.Notice;
import com.backend.vse.service.NoticeService;
import com.backend.vse.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;


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
//            @ApiParam(name = "noticeId", value = "公告序号", required = true)
//            @RequestParam(name="noticeId", required = false, defaultValue = "0") Long nId,
//            @ApiParam(name = "courseId", value = "课程序号", required = true)
//            @RequestParam("courseId") String cId,
//            @ApiParam(name = "noticeTitle", value = "公告标题", required = true)
//            @RequestParam("noticeTitle") String noticeTitle,
//            @ApiParam(name = "publishTime", value = "发布时间", required = true)
//            @RequestParam("publishTime") Timestamp publishTime,
//            @ApiParam(name = "content", value = "公告内容", required = true)
//            @RequestParam(value = "content") String content,
            @RequestBody Map<String, String> jsonload,
            HttpServletRequest request
    ) {
        long courseId,noticeId;
        try {
            courseId = Long.parseUnsignedLong(jsonload.get("courseId"));
            noticeId = Long.parseUnsignedLong(jsonload.get("noticeId"));
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }


        Notice notice=new Notice();
        if(noticeId!=0) {
            notice.setNoticeId(noticeId);
        }else{
            notice.setNoticeId(null);
        }
        notice.setCourseId(courseId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(jsonload.get("publishTime"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        notice.setTime(timestamp);
//        MultipartFile file = new MockMultipartFile("file.txt", jsonload.get("content").getBytes());
//        String contentUrl = ossService.uploadFile(file);
//        notice.setContent(contentUrl);
        notice.setContent(jsonload.get("content"));
        notice.setTitle(jsonload.get("noticeTitle"));
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

    @ApiOperation("根据公告ID，删除该公告")
    @DeleteMapping("coursenotice")
    public Result<String> DeleteCourseNotices(@ApiParam(name = "noticeId", value = "公告序号", required = true)
            @RequestParam("noticeId") Long noticeId) {
        String result = noticeService.deleteByNoticeId(noticeId);
        return Result.success(result);
    }
}
