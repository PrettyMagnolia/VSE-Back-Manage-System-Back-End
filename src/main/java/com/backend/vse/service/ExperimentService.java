package com.backend.vse.service;

import com.backend.vse.dto.ExperimentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExperimentService {
    List<ExperimentDto> selectExperimentByCourseId(Long courseId);
}
