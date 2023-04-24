package com.backend.vse.entity;

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
public class ExperimentReport {
    @JsonSerialize(using = ToStringSerializer.class)
    Long reportId;
    @JsonSerialize(using = ToStringSerializer.class)
    Long stuId;
    @JsonSerialize(using = ToStringSerializer.class)
    Long experimentId;
    @JsonSerialize(using = ToStringSerializer.class)
    Long courseId;
    String reportUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Timestamp submitTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Timestamp reviewTime;
    @JsonSerialize(using = ToStringSerializer.class)
    Float score;
}
