package com.iweb.servlet;

import cn.hutool.json.JSONUtil;
import com.iweb.bean.Admin;
import com.iweb.bean.ResultVo;
import com.iweb.bean.User;
import com.iweb.bean.UserDTO;
import com.iweb.service.UserService;
import com.iweb.service.impl.UserServiceImpl;
import com.iweb.util.DataUtil;
import com.iweb.util.FormBeanUtil;
import com.iweb.util.ImageUtil;
import com.iweb.util.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午4:31
 */
@WebServlet("/user")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5,    // 5 MB
        maxRequestSize = 1024 * 1024 * 5 * 5) // 25 MB
public class UserServlet extends BaseServlet {
    private final static UserService USER_SERVICE = new UserServiceImpl();

    private String generateCookieValue() {
        byte[] randomBytes = new byte[32]; // 32字节随机数
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    private void rememberUser(HttpServletRequest req, HttpServletResponse resp, User user) {
        // 将用户信息存储在 Cookie 中
        Cookie c = new Cookie(DataUtil.COOKIE_NAME, generateCookieValue());
        c.setMaxAge(7 * 24 * 60 * 60);
        resp.addCookie(c);
        // cookie与用户的绑定信息存入COOKIE_USER_MAP
        DataUtil.add(c, user);
        // cookie存入application中
        Object userCookies = req.getServletContext().getAttribute(DataUtil.APPLICATION_COOKIE_NAME);
        List<Cookie> cookies;
        if (userCookies == null) {
            cookies = new ArrayList<>();
        } else {
            cookies = (List<Cookie>) userCookies;
        }
        cookies.add(c);
        req.getServletContext().setAttribute(DataUtil.APPLICATION_COOKIE_NAME, cookies);
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultVo resultVo = new ResultVo();
        // 获取用户输入验证码
        String code = req.getParameter("code");
        // 获取正确的验证码
        HttpSession session = req.getSession();
        String correctCode = (String) session.getAttribute("code");
        if (!correctCode.equalsIgnoreCase(code)) {
            resultVo.setMess("验证码错误");
        } else {
            User user = FormBeanUtil.formToBean(req.getParameterMap(), User.class);
            user = USER_SERVICE.login(user);

            if (user == null) {
                resultVo.setMess("用户名或密码错误");
            } else {
                if ("0".equals(user.getStatus())) {
                    resultVo.setMess("该用户已被冻结，请联系管理员解冻!");
                } else {
                    if ("checked".equals(req.getParameter("remember"))) {
                        // 用户选择了记住账号
                        rememberUser(req, resp, user);
                    }
                    resultVo.setOk(true);
                    session.setAttribute("user", user);
                }
            }
        }
        resp.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }

    public void adminLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultVo resultVo = new ResultVo();

        Admin admin = FormBeanUtil.formToBean(req.getParameterMap(), Admin.class);
        admin = USER_SERVICE.adminLogin(admin);

        if (admin == null) {
            resultVo.setMess("用户名或密码错误");
        } else {
            resultVo.setOk(true);
            req.getSession().setAttribute("admin", admin);
        }
        resp.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }

    public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = FormBeanUtil.formToBean(req.getParameterMap(), User.class);
        ResultVo resultVo = new ResultVo();
        if (USER_SERVICE.verifyUsername(user.getUsername())) {
            resultVo.setMess("用户名已存在,请更改用户名");
        } else {
            if (USER_SERVICE.add(user)) {
                resultVo.setOk(true);
                resultVo.setMess("注册成功");
            } else {
                resultVo.setMess("输入有误,请重新输入");
            }
        }
        resp.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }

    public void editUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        ResultVo vo = new ResultVo();
        if (USER_SERVICE.update(user, req)) {
            vo.setOk(true);
            vo.setMess("修改成功");
        } else {
            vo.setOk(false);
            vo.setMess("修改失败,该用户名已存在!");
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }

    public void getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setAvatar(ImageUtil.encodeImage(user.getAvatar()));
        ResultVo<UserDTO> vo = new ResultVo<>();
        vo.setT(userDTO);
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }

    public void logOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 移除用户会话中的"user"属性
        req.getSession().removeAttribute("user");
        // 遍历请求中的所有Cookie
        for (Cookie cookie : req.getCookies()) {
            // 如果Cookie的名称与定义的COOKIE_NAME相同
            if (cookie.getName().equals(DataUtil.COOKIE_NAME)) {
                // 根据Cookie的值删除用户
                DataUtil.removeUserByCookie(cookie);
                Object userCookies = req.getServletContext().getAttribute(DataUtil.APPLICATION_COOKIE_NAME);
                List<Cookie> cookies;
                if (userCookies != null) {
                    cookies = (List<Cookie>) userCookies;
                    // 遍历用户Cookie列表
                    Iterator<Cookie> iterator = cookies.iterator();
                    while (iterator.hasNext()) {
                        Cookie c = iterator.next();
                        if (c.getValue().equals(cookie.getValue())) {
                            iterator.remove();
                            break;
                        }
                    }
                }
                break;
            }
        }
        // 跳转至登录页面
        resp.sendRedirect("login.html");
    }

    public void adminLogOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 移除用户会话中的"user"属性
        req.getSession().removeAttribute("admin");
        // 跳转至登录页面
        resp.sendRedirect("admin/login.html");
    }

    public void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        User user = (User) req.getSession().getAttribute("user");
        ResultVo vo = new ResultVo();
        if (!user.getPassword().equals(MD5Util.getMD5(currentPassword))) {
            vo.setOk(false);
            vo.setMess("当前密码不正确!");
        } else {
            if (USER_SERVICE.updatePassword(user, newPassword)) {
                vo.setOk(true);
                vo.setMess("修改成功!");
            } else {
                vo.setOk(false);
                vo.setMess("修改失败，请重新尝试!");
            }
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }

    public void updateAdminPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        ResultVo vo = new ResultVo();
        if (!admin.getPassword().equals(MD5Util.getMD5(currentPassword))) {
            vo.setOk(false);
            vo.setMess("当前密码不正确!");
        } else {
            if (USER_SERVICE.updateAdminPassword(admin, newPassword)) {
                vo.setOk(true);
                vo.setMess("修改成功!");
            } else {
                vo.setOk(false);
                vo.setMess("修改失败，请重新尝试!");
            }
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }

    public void updateAdminUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newUsername = req.getParameter("newUsername");
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        ResultVo vo = new ResultVo();
        if (USER_SERVICE.updateAdminUsername(admin, newUsername)) {
            vo.setOk(true);
            vo.setMess("修改成功!");
        } else {
            vo.setOk(false);
            vo.setMess("修改失败，请重新尝试!");
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }

    public void getAllUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = USER_SERVICE.queryAllUsers();
        resp.getWriter().write(JSONUtil.toJsonStr(users));
    }

    public void searchUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("searchTerm");
        List<User> users = USER_SERVICE.queryByUsername(username);
        resp.getWriter().write(JSONUtil.toJsonStr(users));
    }

    public void freezeUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        ResultVo vo = new ResultVo();
        if (USER_SERVICE.freeze(userId)) {
            vo.setOk(true);
        } else {
            vo.setOk(false);
            vo.setMess("冻结失败,请重试！");
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }

    public void unfreezeUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        ResultVo vo = new ResultVo();
        if (USER_SERVICE.unfreeze(userId)) {
            vo.setOk(true);
        } else {
            vo.setOk(false);
            vo.setMess("解冻失败,请重试！");
        }
        resp.getWriter().write(JSONUtil.toJsonStr(vo));
    }
}
