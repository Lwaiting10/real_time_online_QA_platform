package com.iweb.service;

import com.iweb.bean.Category;

import java.util.List;

/**
 * @author Liu Xiong
 * @date 13/12/2023 下午2:57
 */
public interface CategoryService {
    List<Category> queryAllCategories();

    Category queryByCategoryId(String categoryId);

    boolean add(Category category);

    boolean delete(String categoryId);
}
