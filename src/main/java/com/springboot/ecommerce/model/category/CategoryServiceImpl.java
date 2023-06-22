package com.springboot.ecommerce.model.category;


import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final ProductServiceImpl productService;

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            return optionalCategory.get();
        } else {
            throw new IllegalStateException("Category not found for id: " + id);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        List<Product> productList = productService.findAllByCategory(id);
        Category category = categoryRepository.findById(id).orElse(null);
        for (Product product : productList) {
            product.getCategories().remove(category);
            productService.saveProduct(product);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategoriesExcept(Long categoryId) {
        List<Long> subCategories = this.getAllSubCategoriesOf(categoryId);
        subCategories.add(categoryId);
        return categoryRepository.getAllCategoriesExceptId(subCategories);
    }

    @Override
    public List<Long> getAllSubCategoriesOf(Long categoryParentId) {
        return categoryRepository.getAllSubCategoriesOf(categoryParentId);
    }

    @Override
    public List<Long> getAllCategoryParent() {
        return categoryRepository.getALlCategoryParent();
    }
}
