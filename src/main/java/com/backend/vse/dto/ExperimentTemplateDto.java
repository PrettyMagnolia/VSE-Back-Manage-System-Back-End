package com.backend.vse.dto;

import com.backend.vse.entity.Experiment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperimentTemplateDto {
    String file;//实验报告模板url

    public ExperimentTemplateDto(Experiment experiment){
        this.file = experiment.getTemplate();
    }
}
