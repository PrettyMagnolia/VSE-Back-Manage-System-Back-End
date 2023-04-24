package com.backend.vse.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author 赵帅涛
 * @date 2023/04/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiveReportScoreDto {
    @JsonSerialize(using = ToStringSerializer.class)
    Long reportId;
    @JsonSerialize(using = ToStringSerializer.class)
    Float score;
    @JsonSerialize(using = ToStringSerializer.class)
    Long reviewerId;
    @JsonSerialize(using = ToStringSerializer.class)
    Boolean reviewerRole;
}
