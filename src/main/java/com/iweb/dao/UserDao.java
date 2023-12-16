package com.iweb.dao;

import com.iweb.bean.Admin;
import com.iweb.bean.User;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 上午11:54
 */
public interface UserDao {
    /**
     * 登录方法
     *
     * @param user 要登录的用户对象
     * @return null
     */
    User login(User user);

    Admin adminLogin(Admin admin);

    /**
     * 查询所有用户
     *
     * @return 所有用户列表
     */
    List<User> queryAllUsers();

    /**
     * 通过用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户对象，如果查询不到则返回null
     */
    User queryByUserId(String userId);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户对象，如果用户名为空则返回null
     */
    List<User> queryByUsername(String username);

    /**
     * 验证用户名是否已存在
     *
     * @param username 要验证的用户名
     * @return 如果用户名不存在，则返回false；否则返回true
     */
    boolean verifyUsername(String username);

    /**
     * 添加用户
     *
     * @param user 待添加的用户
     * @return 是否成功添加用户，返回false表示添加失败
     */
    boolean add(User user);

    /**
     * 更新用户信息
     *
     * @param user 待更新的用户对象
     * @return 更新是否成功
     */
    boolean update(User user);

    /**
     * 根据用户ID删除数据
     *
     * @param userId 用户ID
     * @return 删除是否成功。如果删除失败返回false
     */
    boolean delete(String userId);

    /**
     * 查询所有未冻结的用户
     *
     * @return 包含所有未冻结用户的列表，如果不存在未冻结用户则返回null
     */
    List<User> queryAllUnfrozenUser();

    /**
     * 查询所有冻结用户的方法
     *
     * @return 冻结的用户列表
     */
    List<User> queryAllFrozenUser();

    /**
     * 冻结用户
     *
     * @param userId 用户ID
     */
    boolean freeze(String userId);

    /**
     * 解冻用户
     *
     * @param userId 用户ID
     */
    boolean unfreeze(String userId);
}
