package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "course")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {
    @JsonSerialize(using= ToStringSerializer.class)
    Long courseId;
    String courseName;
}
