package com.backend.vse.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendCourseDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long courseId;
    String stuId;
    String school;
    String Name;
}
