package com.iweb.dao.impl;

import com.iweb.bean.Question;
import com.iweb.bean.QuestionDTO;
import com.iweb.dao.LikeDao;
import com.iweb.dao.QuestionDao;
import com.iweb.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午2:17
 */
public class QuestionDaoImpl implements QuestionDao {
    private final QueryRunner qr =
            new QueryRunner(DruidUtil.getDataSource());
    private final LikeDao likeDao = new LikeDaoImpl();

    @Override
    public List<Question> queryAllQuestions() {
        List<Question> questions = null;
        String sql = "select * from question";
        try {
            questions = qr.query(sql, new BeanListHandler<>(Question.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public List<QuestionDTO> queryQuestionDTOByStatus(String status, String title, String category) {
        List<QuestionDTO> list = null;
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT q.`questionId`, q.`title`,q.`text`,q.`createTime`, u.`username`, c.`categoryName`,q.`status` FROM question q ")
                .append("LEFT JOIN USER u  ON u.`userId`=q.`userId` ")
                .append("LEFT JOIN category c ON c.`categoryId`=q.`categoryId` ")
                .append("WHERE q.status = ?");

        List<Object> params = new ArrayList<>();
        params.add(status);

        // 判断是否需要添加标题模糊查询条件
        if (title != null && !title.isEmpty()) {
            sqlBuilder.append(" AND q.title LIKE ?");
            params.add("%" + title + "%");
        }

        // 判断是否需要添加分类查询条件
        if (category != null && !category.isEmpty()) {
            sqlBuilder.append(" AND c.categoryName = ?");
            params.add(category);
        }
        sqlBuilder.append(" ORDER BY q.`createTime` DESC");
        String sql = sqlBuilder.toString();

        try {
            // 将参数列表转换为数组，并执行查询
            Object[] paramsArray = params.toArray();
            list = qr.query(sql, new BeanListHandler<>(QuestionDTO.class), paramsArray);

            for (QuestionDTO questionDTO : list) {
                // 获取问题的点赞数量
                questionDTO.setLikes(likeDao.queryCountByTargetId(questionDTO.getQuestionId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Question queryByQuestionId(String questionId) {
        String sql = "select * from question where questionId = ?";
        Question question = null;
        try {
            question = qr.query(sql, new BeanHandler<>(Question.class), questionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    @Override
    public List<Question> queryByCategoryId(String categoryId) {
        List<Question> questions = null;
        String sql = "select * from question where categoryId = ?";
        try {
            questions = qr.query(sql, new BeanListHandler<>(Question.class), categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public List<Question> queryByTitle(String title) {
        List<Question> questions = null;
        String sql = "select * from question where title like ?";
        try {
            questions = qr.query(sql, new BeanListHandler<>(Question.class), "%" + title + "%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public List<Question> queryByUserId(String userId) {
        List<Question> questions = null;
        String sql = "select * from question where userId = ?";
        try {
            questions = qr.query(sql, new BeanListHandler<>(Question.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public List<QuestionDTO> queryQuestionDTOByUserId(String userId) {
        List<QuestionDTO> list = null;
        String sql = "SELECT q.`questionId`,q.`title`,q.`createTime`,u.`username`,c.`categoryName`,q.`status` FROM question q \n" +
                "LEFT JOIN USER u  ON u.`userId`=q.`userId`\n" +
                "LEFT JOIN category c ON c.`categoryId`=q.`categoryId` WHERE q.userId = ?" +
                "ORDER BY q.`createTime` DESC";
        try {
            list = qr.query(sql, new BeanListHandler<>(QuestionDTO.class), userId);
            for (QuestionDTO questionDTO : list) {
                // 获取问题的点赞数量
                questionDTO.setLikes(likeDao.queryCountByTargetId(questionDTO.getQuestionId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean update(Question question) {
        String sql = "update question set title = ?,text=?,categoryId=? where questionId = ?";
        try {
            int update = qr.update(sql, question.getTitle(), question.getText(), question.getCategoryId(), question.getQuestionId());
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStatus(String questionId, String status) {
        String sql = "update question set status=? where questionId = ?";
        try {
            int update = qr.update(sql, status, questionId);
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(Question question) {
        String sql = "insert into question(questionId,userId,createTime,title,text,categoryId) values(?,?,?,?,?,?)";
        try {
            int update = qr.update(sql, question.getQuestionId(), question.getUserId(), question.getCreateTime(), question.getTitle(), question.getText(), question.getCategoryId());
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String questionId) {
        String sql = "delete from question where questionId = ?";
        try {
            int update = qr.update(sql, questionId);
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
