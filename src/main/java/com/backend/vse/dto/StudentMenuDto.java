package com.backend.vse.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentMenuDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long id;
    String title;
    String name;
    String kind;
    String content;
    String file;
}
