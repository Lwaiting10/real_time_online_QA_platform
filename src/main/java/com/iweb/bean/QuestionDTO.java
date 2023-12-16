package com.iweb.bean;

import lombok.Data;

/**
 * @author Liu Xiong
 * @date 14/12/2023 下午12:18
 */
@Data
public class QuestionDTO {
    private String questionId;
    private String title;
    private String text;
    private String createTime;
    private String username;
    private String categoryName;
    private int likes;
    private String status;
}
