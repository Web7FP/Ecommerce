package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.model.tag.Tag;
import com.springboot.ecommerce.model.tag.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tag-management")
public class TagController {
    private final TagServiceImpl tagService;

    @GetMapping("add-new-tag.html")
    public String getAddNewProductTag(Model model){
        Tag tag = new Tag();
        model.addAttribute("tag", tag);
        return "update-tag-form";
    }

    @PostMapping("save-product-tag")
    public String saveNewProductTag(@ModelAttribute("tag") Tag tag){
        tagService.saveProductTag(tag);
        return "redirect:/tag-management/tags-list";
    }

    @GetMapping("tags-list")
    public String viewTagList(Model model){
        model.addAttribute("tagList", tagService.getAllProductTags());
        return "management-tag";
    }


    @GetMapping("delete-tag/{id}")
    public String deleteTag(@PathVariable("id") Long id){
        tagService.deleteTag(id);
        return "redirect:/tag-management/tags-list";
    }

    @GetMapping("update-tag-form/{id}")
    public String updateTag(@PathVariable("id") Long id, Model model){
        model.addAttribute("tag", tagService.getTagById(id));
        return "update-tag-form";
    }
}
