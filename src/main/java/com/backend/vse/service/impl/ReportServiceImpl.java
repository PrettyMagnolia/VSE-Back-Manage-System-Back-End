package com.backend.vse.service.impl;

import com.backend.vse.dto.ExperimentSubmitDto;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.ExperimentReview;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.mapper.CourseExperimentMapper;
import com.backend.vse.mapper.ReviewMapper;
import com.backend.vse.mapper.SubmitMapper;
import com.backend.vse.service.ReportService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    SubmitMapper submitMapper;

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    CourseExperimentMapper courseExperimentMapper;

    @Override
    public int insertOneSubmit(ExperimentSubmit experimentSubmit) {
        return submitMapper.insert(experimentSubmit);
    }

    @Override
    public int insertOneReview(ExperimentReview experimentReview) {
        return reviewMapper.insert(experimentReview);
    }

    @Override
    public ExperimentSubmitDto selectByIndexAndExperimentId(Long index, Long experimentId) {
        ExperimentSubmit experimentSubmit = submitMapper.selectByIndexAndExperimentId(index, experimentId);
        if (experimentSubmit == null) {
            return null;
        }
        ExperimentSubmitDto experimentSubmitDto = new ExperimentSubmitDto(experimentSubmit);
        return experimentSubmitDto;
    }

    /**
     * @author 赵帅涛
     * @date 2023/04/14
     */
    @Override
    public ExperimentSubmit submitReport(ExperimentSubmit experimentSubmit) {
        Long stuId = experimentSubmit.getIndex();
        Long courseId = experimentSubmit.getCourseId();
        Long experimentId = experimentSubmit.getExperimentId();
        Timestamp submitTime = experimentSubmit.getTime();
        String reportUrl = experimentSubmit.getContent();

        if (stuId == null || courseId == null || experimentId == null || submitTime == null || reportUrl == null) {
            return null;
        }

        CourseExperiment currentExperiment = courseExperimentMapper.getOneCourseExperiment(courseId, experimentId);

        if (submitTime.after(currentExperiment.getEndTime()) || submitTime.before(currentExperiment.getStartTime())) {
            return null;
        }
        ExperimentSubmit result = new ExperimentSubmit(null, stuId, experimentId, courseId, reportUrl, submitTime);
        ExperimentSubmit beforeSubmit = submitMapper.selectByIndexExpIdCourseId(stuId, experimentId, courseId);
        if (beforeSubmit == null) {
            submitMapper.firstInsertSubmit(stuId, experimentId, courseId, reportUrl, submitTime);
            Long reportId = submitMapper.getLastReportId();
            result.setReportId(reportId);
        } else {
            submitMapper.updateReport(beforeSubmit.getReportId(), submitTime, reportUrl);
            result.setReportId(beforeSubmit.getReportId());
            result.setContent(reportUrl);
            result.setTime(submitTime);
        }
        return result;
    }
}
