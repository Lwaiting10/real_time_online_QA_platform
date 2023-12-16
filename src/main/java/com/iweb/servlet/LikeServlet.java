package com.iweb.servlet;

import cn.hutool.json.JSONUtil;
import com.iweb.bean.Like;
import com.iweb.bean.ResultVo;
import com.iweb.bean.User;
import com.iweb.service.LikeService;
import com.iweb.service.impl.LikeServiceImpl;
import com.iweb.util.FormBeanUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * @author Liu Xiong
 * @date 15/12/2023 下午7:29
 */
@WebServlet("/like")
public class LikeServlet extends BaseServlet {
    private final static LikeService LIKE_SERVICE = new LikeServiceImpl();

    public void addLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Like like = FormBeanUtil.formToBean(req.getParameterMap(), Like.class);
        User user = (User) req.getSession().getAttribute("user");
        like.setUserId(user.getUserId());
        ResultVo vo = new ResultVo();
        if (LIKE_SERVICE.add(like)) {
            vo.setOk(true);
            vo.setMess("点赞成功！");
        } else {
            vo.setOk(false);
            vo.setMess("您已经点过赞了，不可以重复点赞");
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }
}
