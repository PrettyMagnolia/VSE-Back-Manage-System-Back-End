package com.backend.vse.dto;

import com.backend.vse.entity.Experiment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperimentContentDto {
    String title;
    String content;
    String file;//实验指导书url

    public ExperimentContentDto(Experiment experiment){
        this.title = experiment.getExperimentName();
        this.content = experiment.getTemplate();
        this.file = experiment.getInstructor();
    }
}
