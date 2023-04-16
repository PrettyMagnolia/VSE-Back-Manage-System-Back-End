package com.backend.vse.service;

import com.backend.vse.dto.ExperimentBriefInfo;
import com.backend.vse.dto.ExperimentContentDto;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.ExperimentTemplateDto;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.Experiment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExperimentService {

    List<Experiment> selectAllExperiments();
    List<ExperimentDto> selectExperimentByCourseId(Long courseId);
    ExperimentContentDto selectExperimentContentById(Long experimentId);
    ExperimentTemplateDto selectExperimentTemplateById(Long experimentId);

    int modifyExperimentInCourse(CourseExperiment courseExperiment);
    int deleteExperimentInCourse(Long courseId, Long experimentId);
}
