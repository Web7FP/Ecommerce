package com.springboot.ecommerce.controller;



import com.springboot.ecommerce.model.category.Category;
import com.springboot.ecommerce.model.category.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category-management")
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @GetMapping("add-category.html")
    public String getAddCategory(Model model){
        Category category = new Category();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        return "add-category";
    }

    @PostMapping("save-category")
    public String saveNewCategory(@ModelAttribute("category") Category category){
        categoryService.saveCategory(category);
        return "redirect:/home";
    }
}
