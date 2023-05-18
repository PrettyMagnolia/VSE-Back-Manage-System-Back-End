package com.backend.vse.service.impl;

import com.backend.vse.dto.SingleExperimentScoreDto;
import com.backend.vse.dto.StudentScoreDto;
import com.backend.vse.entity.Experiment;
import com.backend.vse.entity.Score;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.mapper.ScoreMapper;
import com.backend.vse.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreMapper scoreMapper;


    @Override
    public List<StudentScoreDto> selectScoreListByCourseId(Long courseId) {
        //查询该课程的所有学生以及其总成绩
        List<StudentScoreDto> studentScoreDtoList = scoreMapper.selectScoreListByCourseId(courseId);

        //查询该课程所有实验的最大分数
        List<Long> experimentIdList = scoreMapper.selectExperimentIdsByCourseId(courseId);
        Map<Long, Integer> maxScoreMap = new HashMap<>();//存储experimentId->maxScore的键值对

        for(Long experimentId : experimentIdList){
            int maxScore = scoreMapper.selectMaxScore(courseId, experimentId);
            maxScoreMap.put(experimentId, maxScore);
        }

        for(StudentScoreDto item : studentScoreDtoList){
            //查询该学生该课程的所有实验的成绩，然后更新总成绩
            float totalScore = 0;
            Long index = item.getIndex();

            List<SingleExperimentScoreDto> singleExperimentScoreDtoList = scoreMapper.selectSingleExperimentScoreDto(index, courseId);
            //给该学生的每个实验填上maxScore
            for(SingleExperimentScoreDto s : singleExperimentScoreDtoList){
                if(index == 42){
                    System.out.println("hym");
                }
                Long experimentId = s.getExperimentId();
                int maxScore = maxScoreMap!=null ? maxScoreMap.get(experimentId) : 0;
                s.setMaxScore(maxScore);
                totalScore += s.getScore();
            }
            //用totalScore更新score表
            int res = scoreMapper.updateScore(index, courseId, totalScore);
            //用新算出来的totalScore作为总成绩
            item.setTotalScore(totalScore);

            item.setScoreList(singleExperimentScoreDtoList);
        }



        return studentScoreDtoList;
    }
}
