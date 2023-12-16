package com.iweb.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 14/12/2023 下午10:26
 */
@Data
public class AnswerDTO {
    private String answerId;
    private String questionId;
    private String title;
    private String username;
    private String createTime;
    private String text;
    private int likes;
    List<AnswerDTO> answer;
}
