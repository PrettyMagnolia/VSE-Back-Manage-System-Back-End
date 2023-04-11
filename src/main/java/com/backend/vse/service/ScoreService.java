package com.backend.vse.service;

import com.backend.vse.dto.StudentScoreDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScoreService {
    List<StudentScoreDto> selectScoreListByCourseId(Long courseId);
}
