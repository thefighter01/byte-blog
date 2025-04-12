package com.fighter.byteblog.services;


import com.fighter.byteblog.domain.entities.Category;

import java.util.List;
import java.util.UUID;


public interface CategoryService {

    List<Category> listCategories();

    Category createCategory(Category category);

    void deleteCategory(UUID categoryId);
}
