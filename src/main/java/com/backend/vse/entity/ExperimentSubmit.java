package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "experiment_submit")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExperimentSubmit {
    @JsonSerialize(using= ToStringSerializer.class)
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    Long reportId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long index;
    @JsonSerialize(using= ToStringSerializer.class)
    Long experimentId;
    @JsonSerialize(using= ToStringSerializer.class)
    Long courseId;
    String content;
    Timestamp time;
}
