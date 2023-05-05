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

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "experiment")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Experiment {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.AUTO)
    Long experimentId;
    @JsonSerialize(using= ToStringSerializer.class)
    String experimentName;
    String kind;
    String name;
    String instructor;
    String template;
}
