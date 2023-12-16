package com.iweb.dao;

import com.iweb.bean.Category;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午2:57
 */
public interface CategoryDao {
    List<Category> queryAllCategories();

    Category queryByCategoryId(String categoryId);

    boolean add(Category category);

    boolean delete(String categoryId);
}
