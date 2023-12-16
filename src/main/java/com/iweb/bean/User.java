package com.iweb.bean;

import lombok.Data;

/**
 * @author Liu Xiong
 * @date 13/12/2023 上午11:44
 */
@Data
public class User {
    private String userId;
    private String username;
    private String password;
    private String phone;
    private String email;
    private byte[] avatar;
    private String status;
}
