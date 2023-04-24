package com.backend.vse.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

/**
 * @author 赵帅涛
 * @date 2023/04/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSimpleDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long stuId;
    String schoolNumber;
    String stuName;
    String school;
}
