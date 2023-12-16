package com.iweb.dao;

import com.iweb.bean.Answer;
import com.iweb.bean.AnswerDTO;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午3:04
 */
public interface AnswerDao {
    /**
     * 根据答案ID查询答案
     *
     * @param answerId 答案ID
     * @return 查询结果
     */
    Answer queryByAnswerId(String answerId);

    /**
     * 根据用户ID查询答案列表
     *
     * @param userId 用户ID
     * @return 答案列表
     */
    List<Answer> queryByUserId(String userId);

    List<AnswerDTO> queryAnswerDTOByUserId(String userId);

    /**
     * 根据问题ID查询回答列表
     *
     * @param questionId 问题ID
     * @return 回答列表
     */
    List<Answer> queryByQuestionId(String questionId);

    List<AnswerDTO> queryAnswerDTOListByQuestionId(String questionId);

    /**
     * 添加回答
     *
     * @param answer 需要添加的回答
     * @return 如果回答已成功添加到集合中则返回false，否则返回true
     */
    boolean add(Answer answer);

    /**
     * 删除指定回答
     *
     * @param answerId 回答的唯一标识
     * @return 删除是否成功的布尔值，false表示删除失败
     */
    boolean deleteByAnswerId(String answerId);

    boolean deleteByQuestionId(String questionId);
}
