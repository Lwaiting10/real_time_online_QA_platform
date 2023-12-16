package com.iweb.servlet;

import cn.hutool.json.JSONUtil;
import com.iweb.bean.*;
import com.iweb.service.CategoryService;
import com.iweb.service.QuestionService;
import com.iweb.service.impl.CategoryServiceImpl;
import com.iweb.service.impl.QuestionServiceImpl;
import com.iweb.util.FormBeanUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 14/12/2023 下午12:25
 */
@WebServlet("/question")
public class QuestionServlet extends BaseServlet {
    private final static QuestionService QUESTION_SERVICE = new QuestionServiceImpl();
    private final static CategoryService CATEGORY_SERVICE = new CategoryServiceImpl();

    public void getPassQuestions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String category = req.getParameter("category");
        List<QuestionDTO> questions = QUESTION_SERVICE.queryPassQuestionDTO(title, category);
        resp.getWriter().write(JSONUtil.toJsonStr(questions));
    }

    public void getQuestionDTOList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = req.getParameter("status");
        List<QuestionDTO> questions = QUESTION_SERVICE.queryQuestionDTOListByStatus(status);
        resp.getWriter().write(JSONUtil.toJsonStr(questions));
    }

    public void getCategoryOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = CATEGORY_SERVICE.queryAllCategories();
        resp.getWriter().write(JSONUtil.toJsonStr(categories));
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultVo vo = new ResultVo();
        if (QUESTION_SERVICE.add(req)) {
            vo.setOk(true);
            vo.setMess("提交成功，请等待审核！");
        } else {
            vo.setOk(false);
            vo.setMess("提交失败，请重新提交！");
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }

    public void questionDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questionId = req.getParameter("questionId");
        QuestionDTO questionDTO = QUESTION_SERVICE.queryQuestionDTOByQuestionId(questionId);
        resp.getWriter().write(JSONUtil.toJsonStr(questionDTO));
    }

    public void getUserQuestionHistory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<QuestionDTO> list = QUESTION_SERVICE.queryQuestionDTOByUserId(user.getUserId());
        resp.getWriter().write(JSONUtil.toJsonStr(list));
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questionId = req.getParameter("questionId");
        ResultVo vo = new ResultVo();
        if (QUESTION_SERVICE.delete(questionId)) {
            vo.setOk(true);
            vo.setMess("删除成功!");
        } else {
            vo.setOk(false);
            vo.setMess("删除失败，问题不存在!");
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }

    public void check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questionId = req.getParameter("questionId");
        String approve = req.getParameter("approve");
        ResultVo vo = new ResultVo();
        if (QUESTION_SERVICE.updateStatus(questionId, approve)) {
            vo.setOk(true);
        } else {
            vo.setOk(false);
            vo.setMess("操作失败!");
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }
}
