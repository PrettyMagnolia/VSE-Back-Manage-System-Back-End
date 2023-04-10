package com.backend.vse.service.impl;

import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.ScoreDto;
import com.backend.vse.mapper.ScoreMapper;
import com.backend.vse.service.ScoreService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreMapper scoreMapper;

    @Override
    public List<ScoreDto> selectScoreListByCourseId(Long courseId) {
        List<ScoreDto> scoreDtoList = scoreMapper.selectScoreListByCourseId(courseId).stream()
                                                .map(score -> new ScoreDto(score))
                                                .collect(Collectors.toList());
        return scoreDtoList;
    }
}
