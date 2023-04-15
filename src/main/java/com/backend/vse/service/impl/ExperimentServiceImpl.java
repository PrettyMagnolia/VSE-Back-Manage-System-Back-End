package com.backend.vse.service.impl;

import com.backend.vse.dto.ExperimentBriefInfo;
import com.backend.vse.dto.ExperimentContentDto;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.ExperimentTemplateDto;
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
    public List<ExperimentBriefInfo> selectAllExperimentsIdAndName() {
        return experimentMapper.selectAllExperimentsIdAndName();
    }

    @Override
    public List<ExperimentDto> selectExperimentByCourseId(Long courseId) {
        List<Experiment> experimentList = experimentMapper.selectExperimentsByCourseId(courseId);
        if(experimentList == null || experimentList.size() == 0){
            return new ArrayList<ExperimentDto>();
        }
        List<ExperimentDto> experimentDtoList = experimentList.stream().map(experiment -> new ExperimentDto(experiment))
                                                                        .collect(Collectors.toList());
        return experimentDtoList;
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
