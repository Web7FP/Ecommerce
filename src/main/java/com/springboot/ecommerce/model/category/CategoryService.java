package com.springboot.ecommerce.model.category;

import java.util.List;

public interface CategoryService {
    void saveCategory(Category category);

    List<Category> getAllCategories();

    void deleteCategory(Long id);

    Category getCategoryById(Long id);

    List<Category> getAllCategoriesExcept(Long categoryId);

    List<Long> getAllSubCategoriesOf(Long categoryParentId);
}
