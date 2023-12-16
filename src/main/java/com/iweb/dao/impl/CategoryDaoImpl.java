package com.iweb.dao.impl;

import com.iweb.bean.Category;
import com.iweb.bean.Question;
import com.iweb.dao.CategoryDao;
import com.iweb.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午2:59
 */
public class CategoryDaoImpl implements CategoryDao {
    private final QueryRunner qr =
            new QueryRunner(DruidUtil.getDataSource());

    @Override
    public List<Category> queryAllCategories() {
        List<Category> categories = null;
        String sql = "select * from category";
        try {
            categories = qr.query(sql, new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category queryByCategoryId(String categoryId) {
        String sql = "select * from category where categoryId = ?";
        Category category = null;
        try {
            category = qr.query(sql, new BeanHandler<>(Category.class), categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public boolean add(Category category) {
        String sql = "insert into category(categoryId,categoryName) values(?,?)";
        try {
            int update = qr.update(sql, category.getCategoryId(), category.getCategoryName());
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String categoryId) {
        String sql = "delete from category where categoryId = ?";
        try {
            int update = qr.update(sql, categoryId);
            return update > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
