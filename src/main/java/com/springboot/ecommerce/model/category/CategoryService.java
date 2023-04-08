package com.springboot.ecommerce.model.category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);

    List<Category> getAllCategories();
}
