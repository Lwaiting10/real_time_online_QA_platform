package com.iweb.dao.impl;

import com.iweb.bean.Answer;
import com.iweb.bean.AnswerDTO;
import com.iweb.dao.AnswerDao;
import com.iweb.dao.LikeDao;
import com.iweb.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午3:08
 */
public class AnswerDaoImpl implements AnswerDao {
    private final QueryRunner qr =
            new QueryRunner(DruidUtil.getDataSource());

    private final LikeDao likeDao = new LikeDaoImpl();

    @Override
    public Answer queryByAnswerId(String answerId) {
        String sql = "select * from answer where answerId = ?";
        Answer answer = null;
        try {
            answer = qr.query(sql, new BeanHandler<>(Answer.class), answerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }

    @Override
    public List<Answer> queryByUserId(String userId) {
        String sql = "select * from answer where userId = ?";
        List<Answer> answers = null;
        try {
            answers = qr.query(sql, new BeanListHandler<>(Answer.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

    @Override
    public List<AnswerDTO> queryAnswerDTOByUserId(String userId) {
        String sql = "SELECT a.`questionId`,q.`title`  FROM answer a LEFT JOIN question q ON a.`questionId`=q.`questionId`\n" +
                "WHERE a.`userId`= ? GROUP BY a.`questionId`";
        List<AnswerDTO> list = null;
        try {
            list = qr.query(sql, new BeanListHandler<>(AnswerDTO.class), userId);
            sql = "SELECT a.`answerId`,a.`createTime`,a.`text`,a.questionId,q.title FROM answer a\n" +
                    "LEFT JOIN QUESTION q ON q.`questionId`=a.`questionId` " +
                    "WHERE a.`userId` =? ORDER BY createTime";
            List<AnswerDTO> allList = qr.query(sql, new BeanListHandler<>(AnswerDTO.class), userId);
            for (AnswerDTO answerDTO : allList) {
                // 获取回答的点赞数量
                answerDTO.setLikes(likeDao.queryCountByTargetId(answerDTO.getAnswerId()));
                for (AnswerDTO a : list) {
                    if (a.getQuestionId().equals(answerDTO.getQuestionId())) {
                        if (a.getAnswer() == null) {
                            a.setAnswer(new ArrayList<>());
                        }
                        a.getAnswer().add(answerDTO);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Answer> queryByQuestionId(String questionId) {
        String sql = "select * from answer where questionId = ?";
        List<Answer> answers = null;
        try {
            answers = qr.query(sql, new BeanListHandler<>(Answer.class), questionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

    @Override
    public List<AnswerDTO> queryAnswerDTOListByQuestionId(String questionId) {
        String sql = "SELECT a.`answerId`,u.`username`,a.`createTime`,a.`text` FROM answer a\n" +
                "LEFT JOIN USER u ON u.`userId`=a.`userId` WHERE a.`questionId` =? ORDER BY createTime";
        List<AnswerDTO> list = null;
        try {
            list = qr.query(sql, new BeanListHandler<>(AnswerDTO.class), questionId);
            for (AnswerDTO answerDTO : list) {
                // 获取回答的点赞数量
                answerDTO.setLikes(likeDao.queryCountByTargetId(answerDTO.getAnswerId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean add(Answer answer) {
        String sql = "insert into answer(answerId,userId,questionId,createTime,text) values(?,?,?,?,?)";
        try {
            int update = qr.update(sql, answer.getAnswerId(), answer.getUserId(), answer.getQuestionId(), answer.getCreateTime(), answer.getText());
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByAnswerId(String answerId) {
        String sql = "delete from answer where answerId = ?";
        try {
            int update = qr.update(sql, answerId);
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByQuestionId(String questionId) {
        String sql = "delete from answer where questionId = ?";
        try {
            int update = qr.update(sql, questionId);
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
