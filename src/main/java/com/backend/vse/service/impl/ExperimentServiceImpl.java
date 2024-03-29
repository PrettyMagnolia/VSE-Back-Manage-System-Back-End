package com.backend.vse.service.impl;

import com.backend.vse.dto.*;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.Experiment;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.mapper.StudentAttendCourseMapper;
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

    @Autowired
    StudentAttendCourseMapper studentAttendCourseMapper;

    @Override
    public List<Experiment> selectAllExperiments() {
        return experimentMapper.selectAllExperiments();
    }

    @Override
    public List<ExperimentDto> selectExperimentByCourseId(Long courseId) {
        List<CourseExperiment> courseExperimentList = experimentMapper.selectExperimentsByCourseId(courseId);
        ArrayList<ExperimentDto> res = new ArrayList<>();
        if (courseExperimentList == null || courseExperimentList.size() == 0) {
            return res;
        }
        for (CourseExperiment cs : courseExperimentList) {
            Experiment experiment = experimentMapper.selectExperimentById(cs.getExperimentId());
            ExperimentDto experimentDto = new ExperimentDto(experiment.getExperimentId(), experiment.getExperimentName(), cs.getInstructor(), cs.getTemplate(), cs.getScore(), cs.getStartTime(), cs.getEndTime());
            res.add(experimentDto);
        }
        return res;
    }

    @Override
    public List<Experiment> selectExceptExperimentByCourseId(Long courseId) {
        List<Experiment> allExperiments = experimentMapper.selectAllExperiments();
        List<CourseExperiment> courseExperimentList = experimentMapper.selectExperimentsByCourseId(courseId);
        ArrayList<Experiment> res = new ArrayList<>();
        for (Experiment ex: allExperiments) {
            Long experimentId = ex.getExperimentId();
            boolean flag = true;
            for (CourseExperiment cs: courseExperimentList) {
                if (cs.getExperimentId() == experimentId) {
                    flag = false;
                    break;
                }
            }
            if (flag) res.add(ex);
        }
        return res;
    }

    @Override
    public ExperimentContentDto selectExperimentContentById(Long experimentId) {
        Experiment experiment = experimentMapper.selectExperimentById(experimentId);
        if (experiment == null) {
            return null;
        }
        ExperimentContentDto experimentContentDto = new ExperimentContentDto(experiment);
        return experimentContentDto;
    }

    @Override
    public ExperimentTemplateDto selectExperimentTemplateById(Long experimentId) {
        Experiment experiment = experimentMapper.selectExperimentById(experimentId);
        if (experiment == null) {
            return null;
        }
        ExperimentTemplateDto experimentTemplateDto = new ExperimentTemplateDto(experiment);
        return experimentTemplateDto;
    }

    @Override
    public int insertExperimentInCourse(Long courseId, Long experimentId) {
        return experimentMapper.insertExperimentInCourse(courseId, experimentId);
    }

    @Override
    public int modifyExperimentInCourse(CourseExperiment courseExperiment) {
        return experimentMapper.updateExperimentInCourse(courseExperiment);
    }

    @Override
    public int deleteExperimentInCourse(Long courseId, Long experimentId) {
        return experimentMapper.deleteExperimentInCourse(courseId, experimentId);
    }

    @Override
    public int updateExperimentInstructor(Long courseId, Long experimentId, String instructor) {
        return experimentMapper.updateExperimentInstructor(courseId, experimentId, instructor);
    }

    @Override
    public int updateExperimentTemplate(Long courseId, Long experimentId, String template) {
        return experimentMapper.updateExperimentTemplate(courseId, experimentId, template);
    }

    @Override
    public String findInstructorByIndexAndExperiment(Long index, Long experimentId) {
        String file="";
        ArrayList<CourseExperiment> course_experiments= studentAttendCourseMapper.getExperimentByIndex(index);
        for (CourseExperiment courseExperiment: course_experiments) {
            Experiment experiment=experimentMapper.selectExperimentById(courseExperiment.getExperimentId());
            if(experimentId==experiment.getExperimentId()) {
                file = courseExperiment.getInstructor();
            }
        }
        return file;
    }
}
