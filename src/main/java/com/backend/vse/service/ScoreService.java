package com.backend.vse.service;

import com.backend.vse.dto.ScoreDto;
import com.backend.vse.entity.Score;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScoreService {
    List<ScoreDto> selectScoreListByCourseId(Long courseId);
}
