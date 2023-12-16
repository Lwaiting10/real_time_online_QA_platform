package com.iweb.service.impl;

import com.iweb.bean.Answer;
import com.iweb.bean.AnswerDTO;
import com.iweb.bean.QuestionDTO;
import com.iweb.bean.User;
import com.iweb.dao.AnswerDao;
import com.iweb.dao.LikeDao;
import com.iweb.dao.impl.AnswerDaoImpl;
import com.iweb.dao.impl.LikeDaoImpl;
import com.iweb.service.AnswerService;
import com.iweb.util.DateUtil;
import com.iweb.util.FormBeanUtil;
import com.iweb.util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 14/12/2023 下午1:19
 */
public class AnswerServiceImpl implements AnswerService {
    private final static AnswerDao ANSWER_DAO = new AnswerDaoImpl();
    private final static LikeDao LIKE_DAO = new LikeDaoImpl();

    @Override
    public Answer queryByAnswerId(String answerId) {
        return ANSWER_DAO.queryByAnswerId(answerId);
    }

    @Override
    public List<AnswerDTO> queryAnswerDTOListByQuestionId(String questionId) {
        return ANSWER_DAO.queryAnswerDTOListByQuestionId(questionId);
    }

    @Override
    public List<Answer> queryByUserId(String userId) {
        return ANSWER_DAO.queryByUserId(userId);
    }

    @Override
    public List<AnswerDTO> queryAnswerDTOByUserId(String userId) {
        return ANSWER_DAO.queryAnswerDTOByUserId(userId);
    }

    @Override
    public List<Answer> queryByQuestionId(String questionId) {
        return ANSWER_DAO.queryByQuestionId(questionId);
    }

    @Override
    public boolean add(HttpServletRequest req) {
        Answer answer = FormBeanUtil.formToBean(req.getParameterMap(), Answer.class);
        answer.setAnswerId(UUIDUtil.uuid());
        User user = (User) req.getSession().getAttribute("user");
        answer.setUserId(user.getUserId());
        answer.setCreateTime(DateUtil.dateToString(new Date()));
        return ANSWER_DAO.add(answer);
    }

    @Override
    public boolean deleteByAnswerId(String answerId) {
        // 回答删除，其点赞数据也删除
        if (ANSWER_DAO.deleteByAnswerId(answerId)) {
            LIKE_DAO.deleteByTargetId(answerId);
            return true;
        }
        return false;
    }
}
