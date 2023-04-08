package com.backend.vse.service.impl;

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
    public void insertOneSubmit(ExperimentSubmit experimentSubmit) {
        submitMapper.insert(experimentSubmit);
    }

    @Override
    public void insertOneReview(ExperimentReview experimentReview) {
        reviewMapper.insert(experimentReview);
    }
}
