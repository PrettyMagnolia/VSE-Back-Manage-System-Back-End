package com.backend.vse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportedStudentDto {
    @JsonProperty("学号")
    Long id;
    @JsonProperty("学校")
    String school;
    @JsonProperty("姓名")
    String name;
    @JsonProperty("性别")
    String gender;
    @JsonProperty("邮箱")
    String email;
}
