package com.backend.vse.entity;

import com.backend.vse.mapper.TeacherTeachCourseMapper;
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
@TableName(value = "teacher_teach_course")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherTeachCourse {
    @JsonSerialize(using= ToStringSerializer.class)
    Long index;
    @JsonSerialize(using= ToStringSerializer.class)
    Long courseId;
    Byte role;

    public TeacherTeachCourse(Long index, Long courseId){
        this.index = index;
        this.courseId = courseId;
    }
}
