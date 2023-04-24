package com.backend.vse.dto;

import com.backend.vse.entity.Experiment;
import com.backend.vse.entity.ExperimentSubmit;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExperimentSubmitDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long reportId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long index;
    @JsonSerialize(using= ToStringSerializer.class)
    Long experimentId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long courseId;
    String content;
    Timestamp time;

    public ExperimentSubmitDto(ExperimentSubmit experimentSubmit){
        this.reportId = experimentSubmit.getReportId();
        this.index = experimentSubmit.getIndex();
        this.experimentId = experimentSubmit.getCourseId();
        this.courseId = experimentSubmit.getCourseId();
        this.content = experimentSubmit.getContent();
        this.time = experimentSubmit.getTime();
    }
}
