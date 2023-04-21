package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/4/15
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "course_experiment")
public class CourseExperiment {
    @JsonSerialize(using= ToStringSerializer.class)
    Long courseId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long experimentId;
    Date startTime;
    Date endTime;
    float score;
    String instructor;
    String template;
}
