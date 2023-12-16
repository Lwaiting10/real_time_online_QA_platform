package com.iweb.service.impl;

import com.iweb.bean.Like;
import com.iweb.dao.LikeDao;
import com.iweb.dao.impl.LikeDaoImpl;
import com.iweb.service.LikeService;
import com.iweb.util.UUIDUtil;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 14/12/2023 下午1:23
 */
public class LikeServiceImpl implements LikeService {
    private final static LikeDao LIKE_DAO = new LikeDaoImpl();

    @Override
    public List<Like> queryByUserId(String userId) {
        return LIKE_DAO.queryByUserId(userId);
    }

    @Override
    public List<Like> queryByType(String type) {
        return LIKE_DAO.queryByType(type);
    }

    @Override
    public int queryCountByTargetId(String targetId) {
        return LIKE_DAO.queryCountByTargetId(targetId);
    }

    @Override
    public boolean add(Like like) {
        // 检查是否重复点赞
        List<Like> list = queryByUserId(like.getUserId());
        for (Like l : list) {
            if (l.getTargetId().equals(like.getTargetId())) {
                return false;
            }
        }
        like.setLikeId(UUIDUtil.uuid());
        return LIKE_DAO.add(like);
    }

    @Override
    public boolean deleteByLikeId(String likeId) {
        return LIKE_DAO.deleteByLikeId(likeId);
    }
}
