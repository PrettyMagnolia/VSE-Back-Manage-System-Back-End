package com.backend.vse.service;

import com.backend.vse.entity.ExperimentReview;
import com.backend.vse.entity.ExperimentSubmit;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    void insertOneSubmit(ExperimentSubmit experimentSubmit);
    void insertOneReview(ExperimentReview experimentReview);
}
