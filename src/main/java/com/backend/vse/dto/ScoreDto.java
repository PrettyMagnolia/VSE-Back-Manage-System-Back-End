package com.backend.vse.dto;

import com.backend.vse.entity.Score;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreDto {
    @JsonSerialize(using= ToStringSerializer.class)
    Long index;
    float score;

    public ScoreDto(Score score){
        this.index = score.getIndex();
        this.score = score.getScore();
    }
}
