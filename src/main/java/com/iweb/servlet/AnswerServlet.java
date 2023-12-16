package com.iweb.servlet;

import cn.hutool.json.JSONUtil;
import com.iweb.bean.*;
import com.iweb.dao.AnswerDao;
import com.iweb.service.AnswerService;
import com.iweb.service.impl.AnswerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 14/12/2023 下午10:21
 */
@WebServlet("/answer")
public class AnswerServlet extends BaseServlet {
    private final static AnswerService ANSWER_SERVICE = new AnswerServiceImpl();

    public void getAnswerList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questionId = req.getParameter("questionId");
        List<AnswerDTO> list = ANSWER_SERVICE.queryAnswerDTOListByQuestionId(questionId);
        resp.getWriter().write(JSONUtil.toJsonStr(list));
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultVo vo = new ResultVo();
        if (ANSWER_SERVICE.add(req)) {
            vo.setOk(true);
            vo.setMess("提交成功！");
        } else {
            vo.setOk(false);
            vo.setMess("提交失败，请重新提交！");
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }

    public void getUserAnswerHistory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<AnswerDTO> list = ANSWER_SERVICE.queryAnswerDTOByUserId(user.getUserId());
        resp.getWriter().write(JSONUtil.toJsonStr(list));
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String answerId = req.getParameter("answerId");
        ResultVo vo = new ResultVo();
        if (ANSWER_SERVICE.deleteByAnswerId(answerId)) {
            vo.setOk(true);
            vo.setMess("删除成功!");
        } else {
            vo.setOk(false);
            vo.setMess("删除失败，数据不存在!");
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }
}
