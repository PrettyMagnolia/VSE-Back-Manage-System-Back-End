package com.backend.vse.service.impl;

import com.backend.vse.dto.StudentScoreDto;
import com.backend.vse.mapper.ScoreMapper;
import com.backend.vse.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreMapper scoreMapper;

    @Override
    public List<StudentScoreDto> selectScoreListByCourseId(Long courseId) {
        List<StudentScoreDto> studentScoreDtoList = scoreMapper.selectScoreListByCourseId(courseId);
        return studentScoreDtoList;
    }
}
