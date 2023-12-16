package com.iweb.dao;

import com.iweb.bean.Question;
import com.iweb.bean.QuestionDTO;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午1:48
 */
public interface QuestionDao {
    /**
     * 查询所有问题
     *
     * @return 所有问题的列表
     */
    List<Question> queryAllQuestions();

    List<QuestionDTO> queryQuestionDTOByStatus(String status, String title, String category);


    /**
     * 根据问题ID查询问题
     *
     * @param questionId 问题ID
     * @return 查询到的问题，如果不存在则返回null
     */
    Question queryByQuestionId(String questionId);

    /**
     * 通过分类ID查询问题列表
     *
     * @param categoryId 分类ID
     * @return 问题列表
     */
    List<Question> queryByCategoryId(String categoryId);

    /**
     * 根据标题查询问题列表
     *
     * @param title 标题信息
     * @return 根据标题查询到的问题列表
     */
    List<Question> queryByTitle(String title);

    /**
     * 根据用户ID查询问题列表
     *
     * @param userId 用户ID
     * @return 问题列表
     */
    List<Question> queryByUserId(String userId);

    List<QuestionDTO> queryQuestionDTOByUserId(String userId);

    /**
     * 更新问题对象的信息
     *
     * @param question 需要更新的问题对象
     * @return 更新是否成功，返回false表示未成功
     */
    boolean update(Question question);

    /**
     * 更新状态
     *
     * @param questionId 题目ID
     * @param status     状态
     * @return 更新结果
     */
    boolean updateStatus(String questionId, String status);

    /**
     * 添加问题
     *
     * @param question 要添加的问题
     * @return 添加结果
     */
    boolean add(Question question);

    /**
     * 删除指定问题
     *
     * @param questionId 要删除的问题ID
     * @return 删除是否成功
     */
    boolean delete(String questionId);
}
