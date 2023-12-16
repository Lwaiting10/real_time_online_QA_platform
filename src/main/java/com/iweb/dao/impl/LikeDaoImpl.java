package com.iweb.dao.impl;

import com.iweb.bean.Like;
import com.iweb.bean.Question;
import com.iweb.dao.LikeDao;
import com.iweb.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午3:24
 */
public class LikeDaoImpl implements LikeDao {
    private final QueryRunner qr =
            new QueryRunner(DruidUtil.getDataSource());

    @Override
    public List<Like> queryByUserId(String userId) {
        List<Like> likes = null;
        String sql = "select * from likes where userId = ?";
        try {
            likes = qr.query(sql, new BeanListHandler<>(Like.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }

    @Override
    public List<Like> queryByType(String type) {
        List<Like> likes = null;
        String sql = "select * from likes where type = ?";
        try {
            likes = qr.query(sql, new BeanListHandler<>(Like.class), type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }

    @Override
    public int queryCountByTargetId(String targetId) {
        String sql = "select count(*) from likes where targetId=?";
        try {
            Number number = qr.query(sql, new ScalarHandler<>(), targetId);
            return number.intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean add(Like like) {
        String sql = "insert into likes(likeId,userId,type,targetId) values(?,?,?,?)";
        try {
            int update = qr.update(sql, like.getLikeId(), like.getUserId(), like.getType(), like.getTargetId());
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByLikeId(String likeId) {
        String sql = "delete from likes where likeId = ?";
        try {
            int update = qr.update(sql, likeId);
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByTargetId(String targetId) {
        String sql = "delete from likes where targetId = ?";
        try {
            int update = qr.update(sql, targetId);
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
