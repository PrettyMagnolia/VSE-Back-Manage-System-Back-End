package com.backend.vse.service.impl;

import com.backend.vse.dto.ExperimentBriefInfo;
import com.backend.vse.dto.ExperimentContentDto;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.ExperimentTemplateDto;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.Experiment;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperimentServiceImpl implements ExperimentService {
    @Autowired
    ExperimentMapper experimentMapper;

    @Override
    public List<Experiment> selectAllExperiments() {
        return experimentMapper.selectAllExperiments();
    }

    @Override
    public List<ExperimentDto> selectExperimentByCourseId(Long courseId) {
        List<CourseExperiment> courseExperimentList = experimentMapper.selectExperimentsByCourseId(courseId);
        ArrayList<ExperimentDto> res = new ArrayList<>();
        if(courseExperimentList == null || courseExperimentList.size() == 0){
            return res;
        }
        for (CourseExperiment cs: courseExperimentList) {
            Experiment experiment = experimentMapper.selectExperimentById(cs.getExperimentId());
            ExperimentDto experimentDto = new ExperimentDto(experiment.getExperimentId(), experiment.getExperimentName(), experiment.getInstructor(), experiment.getTemplate(), cs.getScore(), cs.getStartTime(), cs.getEndTime());
            res.add(experimentDto);
        }
        return res;
    }

    @Override
    public ExperimentContentDto selectExperimentContentById(Long experimentId) {
        Experiment experiment = experimentMapper.selectExperimentById(experimentId);
        if(experiment == null){
            return null;
        }
        ExperimentContentDto experimentContentDto = new ExperimentContentDto(experiment);
        return experimentContentDto;
    }

    @Override
    public ExperimentTemplateDto selectExperimentTemplateById(Long experimentId) {
        Experiment experiment = experimentMapper.selectExperimentById(experimentId);
        if(experiment == null){
            return null;
        }
        ExperimentTemplateDto experimentTemplateDto = new ExperimentTemplateDto(experiment);
        return experimentTemplateDto;
    }
}
