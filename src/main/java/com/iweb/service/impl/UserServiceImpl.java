package com.iweb.service.impl;

import com.iweb.bean.Admin;
import com.iweb.bean.User;
import com.iweb.dao.AdminDao;
import com.iweb.dao.UserDao;
import com.iweb.dao.impl.AdminDaoImpl;
import com.iweb.dao.impl.UserDaoImpl;
import com.iweb.service.UserService;
import com.iweb.util.FormBeanUtil;
import com.iweb.util.MD5Util;
import com.iweb.util.UUIDUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午4:35
 */
public class UserServiceImpl implements UserService {
    private final static UserDao USER_DAO = new UserDaoImpl();
    private final static AdminDao ADMIN_DAO = new AdminDaoImpl();

    @Override
    public User login(User user) {
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        return USER_DAO.login(user);
    }

    @Override
    public Admin adminLogin(Admin admin) {
        admin.setPassword(MD5Util.getMD5(admin.getPassword()));
        return USER_DAO.adminLogin(admin);
    }

    @Override
    public List<User> queryAllUsers() {
        return USER_DAO.queryAllUsers();
    }

    @Override
    public User queryByUserId(String userId) {
        return USER_DAO.queryByUserId(userId);
    }

    @Override
    public List<User> queryByUsername(String username) {
        return USER_DAO.queryByUsername(username);
    }

    @Override
    public boolean verifyUsername(String username) {
        return USER_DAO.verifyUsername(username);
    }

    @Override
    public boolean add(User user) {
        if (verifyUsername(user.getUsername())) {
            return false;
        }
        user.setUserId(UUIDUtil.uuid());
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        return USER_DAO.add(user);
    }

    @Override
    public boolean update(User user, HttpServletRequest req) {
        User newUser = FormBeanUtil.formToBean(req.getParameterMap(), User.class);
        if (!newUser.getUsername().equals(user.getUsername())) {
            if (USER_DAO.verifyUsername(newUser.getUsername())) {
                return false;
            }
        }
        try {
            Part filePart = req.getPart("avatar");
            InputStream fileContent = filePart.getInputStream();
            System.out.println(fileContent.available());
            if (fileContent.available() > 10) {
                // 用户上传了头像
                byte[] headSculptureBytes = readInputStream(fileContent);
                user.setAvatar(headSculptureBytes);
            }
            user.setUsername(newUser.getUsername());
            user.setEmail(newUser.getEmail());
            user.setPhone(newUser.getPhone());
            USER_DAO.update(user);
            return true;
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updatePassword(User user, String password) {
        user.setPassword(MD5Util.getMD5(password));
        return USER_DAO.update(user);
    }

    @Override
    public boolean updateAdminPassword(Admin admin, String password) {
        admin.setPassword(MD5Util.getMD5(password));
        return ADMIN_DAO.update(admin);
    }

    @Override
    public boolean updateAdminUsername(Admin admin, String username) {
        admin.setUsername(username);
        return ADMIN_DAO.update(admin);
    }

    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        return outputStream.toByteArray();
    }

    @Override
    public boolean delete(String userId) {
        return USER_DAO.delete(userId);
    }

    @Override
    public List<User> queryAllUnfrozenUser() {
        return USER_DAO.queryAllUnfrozenUser();
    }

    @Override
    public List<User> queryAllFrozenUser() {
        return USER_DAO.queryAllFrozenUser();
    }

    @Override
    public boolean freeze(String userId) {
        return USER_DAO.freeze(userId);
    }

    @Override
    public boolean unfreeze(String userId) {
        return USER_DAO.unfreeze(userId);
    }
}
