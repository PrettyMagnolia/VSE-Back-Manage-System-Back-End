package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleExperimentMaxScoreDto {
    Long experimentId;
    int maxScore;
}
