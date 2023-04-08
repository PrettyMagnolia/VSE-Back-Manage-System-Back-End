package com.backend.vse.dto;

import com.backend.vse.entity.Experiment;
import com.backend.vse.service.ExperimentService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExperimentDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long experimentId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long courseId;
    float weight;
    Date startTime;
    Date endTime;

    public ExperimentDto(Experiment experiment){
        this.experimentId = experiment.getExperimentId();
        this.courseId = experiment.getCourseId();
        this.weight = experiment.getWeight();
        this.startTime = experiment.getStartTime();
        this.endTime = experiment.getEndTime();
    }
}
