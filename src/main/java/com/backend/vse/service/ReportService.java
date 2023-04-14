package com.backend.vse.service;

import com.backend.vse.dto.ExperimentSubmitDto;
import com.backend.vse.entity.ExperimentReview;
import com.backend.vse.entity.ExperimentSubmit;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    int insertOneSubmit(ExperimentSubmit experimentSubmit);
    int insertOneReview(ExperimentReview experimentReview);
    ExperimentSubmitDto selectByIndexAndExperimentId(Long index, Long experimentId);

    ExperimentSubmit submitReport(ExperimentSubmit experimentSubmit);
}
