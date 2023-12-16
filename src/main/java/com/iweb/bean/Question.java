package com.iweb.bean;

import lombok.Data;

import java.sql.Date;


/**
 * @author Liu Xiong
 * @date 13/12/2023 上午11:47
 */
@Data
public class Question {
    private String questionId;
    private String userId;
    private String createTime;
    private String title;
    private String text;
    private String categoryId;
    private String status;
}
