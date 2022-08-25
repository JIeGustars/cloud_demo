package com.Gu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer Id;
    private String userId;
    private String userName;
    private String userPassword;
    private String userProtection;
}
