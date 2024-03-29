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

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "student_attend_course")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentAttendCourse {
    @JsonSerialize(using= ToStringSerializer.class)
    Long index;
    @JsonSerialize(using= ToStringSerializer.class)
    Long courseId;
}
