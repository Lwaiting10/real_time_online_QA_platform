package com.iweb.service.impl;

import com.iweb.bean.Category;
import com.iweb.dao.CategoryDao;
import com.iweb.dao.impl.CategoryDaoImpl;
import com.iweb.service.CategoryService;
import com.iweb.util.UUIDUtil;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 14/12/2023 下午1:21
 */
public class CategoryServiceImpl implements CategoryService {
    private final static CategoryDao CATEGORY_DAO = new CategoryDaoImpl();

    @Override
    public List<Category> queryAllCategories() {
        return CATEGORY_DAO.queryAllCategories();
    }

    @Override
    public Category queryByCategoryId(String categoryId) {
        return CATEGORY_DAO.queryByCategoryId(categoryId);
    }

    @Override
    public boolean add(Category category) {
        category.setCategoryId(UUIDUtil.uuid());
        return CATEGORY_DAO.add(category);
    }

    @Override
    public boolean delete(String categoryId) {
        return CATEGORY_DAO.delete(categoryId);
    }
}
