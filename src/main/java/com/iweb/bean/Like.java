package com.iweb.bean;

import lombok.Data;

/**
 * @author Liu Xiong
 * @date 13/12/2023 上午11:51
 */
@Data
public class Like {
    private String likeId;
    private String userId;
    private String type;
    private String targetId;
}
