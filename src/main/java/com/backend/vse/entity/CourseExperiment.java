package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
/**
 * @author 赵帅涛
 * @date 2023/04/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("course_experiment")
public class CourseExperiment {
    @JsonSerialize(using = ToStringSerializer.class)
    Long courseId;
    @JsonSerialize(using = ToStringSerializer.class)
    Long experimentId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Timestamp startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Timestamp endTime;
    @JsonSerialize(using = ToStringSerializer.class)
    Integer score;
    String instructor;
    String template;
}
