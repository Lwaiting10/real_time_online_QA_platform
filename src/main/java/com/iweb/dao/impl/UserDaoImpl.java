package com.iweb.dao.impl;

import com.iweb.bean.Admin;
import com.iweb.bean.User;
import com.iweb.dao.UserDao;
import com.iweb.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午12:02
 */
public class UserDaoImpl implements UserDao {
    private final QueryRunner qr =
            new QueryRunner(DruidUtil.getDataSource());

    @Override
    public User login(User user) {
        String sql = "select * from user where username=? and password=?";
        try {
            user = qr.query(sql, new BeanHandler<>(User.class),
                    user.getUsername(), user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Admin adminLogin(Admin admin) {
        String sql = "select * from admin where username=? and password=?";
        try {
            admin = qr.query(sql, new BeanHandler<>(Admin.class), admin.getUsername(), admin.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    @Override
    public List<User> queryAllUsers() {
        List<User> users = null;
        String sql = "select * from user";
        try {
            users = qr.query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User queryByUserId(String userId) {
        String sql = "select * from user where userId = ?";
        User user = null;
        try {
            user = qr.query(sql, new BeanHandler<>(User.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> queryByUsername(String username) {
        String sql = "select * from user where username like ?";
        username = "%" + username + "%";
        List<User> users = null;
        try {
            users = qr.query(sql, new BeanListHandler<>(User.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean verifyUsername(String username) {
        String sql = "select count(*) from user where username=?";
        try {
            Number number = qr.query(sql, new ScalarHandler<>(), username);
            return number.intValue() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(User user) {
        String sql = "insert into user(userId,username,password,phone,email) values(?,?,?,?,?)";
        try {
            int update = qr.update(sql, user.getUserId(), user.getUsername(), user.getPassword(), user.getPhone(), user.getEmail());
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        String sql = "update user set username=?,password=?,phone=?,email=?,avatar=? where userId=?";
        try {
            int update = qr.update(sql, user.getUsername(), user.getPassword(), user.getPhone(), user.getEmail(), user.getAvatar(), user.getUserId());
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String userId) {
        String sql = "delete from user where userId = ?";
        try {
            int update = qr.update(sql, userId);
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> queryAllUnfrozenUser() {
        List<User> users = null;
        String sql = "select * from user where status = 1";
        try {
            users = qr.query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> queryAllFrozenUser() {
        List<User> users = null;
        String sql = "select * from user where status = 0";
        try {
            users = qr.query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean freeze(String userId) {
        String sql = "update user set status = 0 where userId=?";
        try {
            int update = qr.update(sql, userId);
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unfreeze(String userId) {
        String sql = "update user set status = 1 where userId=?";
        try {
            int update = qr.update(sql, userId);
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
