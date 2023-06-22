package com.springboot.ecommerce.model.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    void saveCategory(Category category);

    List<Category> getAllCategories();

    void deleteCategory(Long id);

    Category getCategoryById(Long id);

    List<Category> getAllCategoriesExcept(Long categoryId);

    List<Long> getAllSubCategoriesOf(Long categoryParentId);

    List<Long> getAllCategoryParent();
}
