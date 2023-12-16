package com.iweb.service;

import com.iweb.bean.Like;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午3:15
 */
public interface LikeService {
    List<Like> queryByUserId(String userId);

    List<Like> queryByType(String type);

    int queryCountByTargetId(String targetId);

    boolean add(Like like);

    boolean deleteByLikeId(String likeId);
}
