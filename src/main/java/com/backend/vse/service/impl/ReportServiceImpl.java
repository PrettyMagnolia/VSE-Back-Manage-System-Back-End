package com.backend.vse.service.impl;

import com.backend.vse.dto.ExperimentSubmitDto;
import com.backend.vse.entity.ExperimentReview;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.mapper.ReviewMapper;
import com.backend.vse.mapper.SubmitMapper;
import com.backend.vse.service.ReportService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    SubmitMapper submitMapper;

    @Autowired
    ReviewMapper reviewMapper;

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
        ExperimentSubmitDto experimentSubmitDto = new ExperimentSubmitDto(experimentSubmit);
        return experimentSubmitDto;
    }
}
