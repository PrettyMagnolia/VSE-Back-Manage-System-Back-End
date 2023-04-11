package com.backend.vse.dto;

import com.backend.vse.entity.Score;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentScoreDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long studentId;
    String studentName;
    float score;
}
