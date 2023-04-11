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

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "course")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {
    @JsonSerialize(using= ToStringSerializer.class)
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    Long courseId;
    String courseName;
    String semester;
    int year;

    public Course(String courseName, String semester, int year){
        this.courseName = courseName;
        this.semester = semester;
        this.year = year;
    }
}
