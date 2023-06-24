package com.springboot.ecommerce.search.controller;


import com.springboot.ecommerce.exception.ProductNotFoundException;
import com.springboot.ecommerce.model.category.Category;
import com.springboot.ecommerce.model.tag.Tag;
import com.springboot.ecommerce.search.model.product.ProductElasticSearch;
import com.springboot.ecommerce.search.model.product.ProductElasticSearchServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ElasticSearchController {

    private final ProductElasticSearchServiceImpl productElasticSearchService;


    @GetMapping("/search")
    public String searchProduct(
            @RequestParam(value = "page", defaultValue = "1") int pageNo,
            @RequestParam("q") String keyword,
            @RequestParam(value = "sortField", defaultValue = "titleKeyword") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "category", required = false) List<String> categories,
            @RequestParam(value = "tag", required = false) List<String> tags,
            @RequestParam(value = "upperBoundPrice", required = false) BigDecimal  upperBoundPrice,
            @RequestParam(value = "lowerBoundPrice", required = false) BigDecimal lowerBoundPrice,
            Model model, HttpSession session
    ){
        int pageSize = 4;
        Pageable pageable = productElasticSearchService.findPaginated(pageNo, pageSize, sortField, sortDir);
        Page<ProductElasticSearch> page = productElasticSearchService
                .searchProduct(
                        keyword, categories, tags,
                        lowerBoundPrice, upperBoundPrice,
                        pageable);
        if (page.isEmpty()){
            throw new ProductNotFoundException();
        }
        productElasticSearchService.setFilterAttributeSession(session,keyword, categories, tags, lowerBoundPrice, upperBoundPrice);


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listProducts", page.getContent());
        model.addAttribute("query", keyword);
        model.addAttribute("selectedCategories", categories);
        model.addAttribute("selectedTags", tags);
        model.addAttribute("lowerBoundPrice", lowerBoundPrice);
        model.addAttribute("upperBoundPrice", upperBoundPrice);

        model.addAttribute("categoriesFilter", productElasticSearchService.getCategoriesFilterFromSession(session));
        model.addAttribute("tagsFilter", productElasticSearchService.getTagsFilterFromSession(session));

        return "result-search";
    }


}
