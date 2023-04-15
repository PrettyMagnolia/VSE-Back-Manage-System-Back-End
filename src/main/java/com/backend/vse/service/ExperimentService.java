package com.backend.vse.service;

import com.backend.vse.dto.ExperimentBriefInfo;
import com.backend.vse.dto.ExperimentContentDto;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.ExperimentTemplateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExperimentService {

    List<ExperimentBriefInfo> selectAllExperimentsIdAndName();
    List<ExperimentDto> selectExperimentByCourseId(Long courseId);
    ExperimentContentDto selectExperimentContentById(Long experimentId);
    ExperimentTemplateDto selectExperimentTemplateById(Long experimentId);
}
