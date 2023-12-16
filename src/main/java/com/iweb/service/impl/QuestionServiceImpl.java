package com.iweb.service.impl;

import com.iweb.bean.Question;
import com.iweb.bean.QuestionDTO;
import com.iweb.bean.User;
import com.iweb.dao.*;
import com.iweb.dao.impl.*;
import com.iweb.service.QuestionService;
import com.iweb.util.DateUtil;
import com.iweb.util.FormBeanUtil;
import com.iweb.util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 14/12/2023 下午1:12
 */
public class QuestionServiceImpl implements QuestionService {
    private final static QuestionDao QUESTION_DAO = new QuestionDaoImpl();
    private final static AnswerDao ANSWER_DAO = new AnswerDaoImpl();
    private final static UserDao USER_DAO = new UserDaoImpl();
    private final static CategoryDao CATEGORY_DAO = new CategoryDaoImpl();

    private final static LikeDao LIKE_DAO = new LikeDaoImpl();

    @Override
    public List<Question> queryAllQuestions() {
        return QUESTION_DAO.queryAllQuestions();
    }

    @Override
    public List<QuestionDTO> queryPassQuestionDTO(String title, String category) {
        return QUESTION_DAO.queryQuestionDTOByStatus("1", title, category);
    }

    @Override
    public List<QuestionDTO> queryQuestionDTOListByStatus(String status) {
        return QUESTION_DAO.queryQuestionDTOByStatus(status, "", "");
    }

    @Override
    public QuestionDTO queryQuestionDTOByQuestionId(String questionId) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = QUESTION_DAO.queryByQuestionId(questionId);
        questionDTO.setQuestionId(questionId);
        questionDTO.setTitle(question.getTitle());
        questionDTO.setText(question.getText());
        questionDTO.setCreateTime(question.getCreateTime());
        String username = USER_DAO.queryByUserId(question.getUserId()).getUsername();
        questionDTO.setUsername(username);
        String categoryName = CATEGORY_DAO.queryByCategoryId(question.getCategoryId()).getCategoryName();
        questionDTO.setCategoryName(categoryName);
        int likes = LIKE_DAO.queryCountByTargetId(questionId);
        questionDTO.setLikes(likes);
        return questionDTO;
    }

    @Override
    public Question queryByQuestionId(String questionId) {
        return QUESTION_DAO.queryByQuestionId(questionId);
    }

    @Override
    public List<Question> queryByCategoryId(String categoryId) {
        return QUESTION_DAO.queryByCategoryId(categoryId);
    }

    @Override
    public List<Question> queryByTitle(String title) {
        return QUESTION_DAO.queryByTitle(title);
    }

    @Override
    public List<Question> queryByUserId(String userId) {
        return QUESTION_DAO.queryByUserId(userId);
    }

    @Override
    public List<QuestionDTO> queryQuestionDTOByUserId(String userId) {
        return QUESTION_DAO.queryQuestionDTOByUserId(userId);
    }

    @Override
    public boolean update(Question question) {
        return QUESTION_DAO.update(question);
    }

    @Override
    public boolean updateStatus(String questionId, String status) {
        return QUESTION_DAO.updateStatus(questionId, status);
    }

    @Override
    public boolean add(HttpServletRequest req) {
        Question question = FormBeanUtil.formToBean(req.getParameterMap(), Question.class);
        User user = (User) req.getSession().getAttribute("user");
        question.setUserId(user.getUserId());
        question.setCreateTime(DateUtil.dateToString(new Date()));
        question.setQuestionId(UUIDUtil.uuid());
        return QUESTION_DAO.add(question);
    }

    @Override
    public boolean delete(String questionId) {
        // 问题删除，同时点赞数据和回答数据也会删除
        if (QUESTION_DAO.delete(questionId)) {
            LIKE_DAO.deleteByTargetId(questionId);
            ANSWER_DAO.deleteByQuestionId(questionId);
            return true;
        }
        return false;
    }
}
