package com.iweb.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author Liu Xiong
 * @date 13/12/2023 上午11:49
 */
@Data
public class Answer {
    private String answerId;
    private String userId;
    private String questionId;
    private String createTime;
    private String text;
}
