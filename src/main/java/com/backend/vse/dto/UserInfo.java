package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/3/31
 * @JDKVersion 17.0.4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Account account;
    private String[] permissions;
    private Byte role;

}
