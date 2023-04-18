package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_all_school")
public class School {
    @TableId
    String id;
    String name;
    String location;
    String department;
    String level;
    String remark;
}
