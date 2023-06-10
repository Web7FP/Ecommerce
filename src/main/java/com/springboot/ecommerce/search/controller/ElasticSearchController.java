package com.springboot.ecommerce.search.controller;


import com.springboot.ecommerce.search.model.product.ProductElasticSearch;
import com.springboot.ecommerce.search.model.product.ProductElasticSearchRepository;
import com.springboot.ecommerce.search.model.product.ProductElasticSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ElasticSearchController {

    private final ProductElasticSearchServiceImpl productElasticSearchService;
    private final ProductElasticSearchRepository productElasticSearchRepository;




    @GetMapping("/search")
    public String searchProduct(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam("q") String titleProduct,
            @RequestParam(value = "sortField", defaultValue = "titleKeyword") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "category", required = false) List<String> category,
            @RequestParam(value = "upperBoundPrice", required = false) BigDecimal  upperBoundPrice,
            @RequestParam(value = "lowerBoundPrice", required = false) BigDecimal lowerBoundPrice,
            Model model
    ){
        int pageSize = 4;
        Page<ProductElasticSearch> page = productElasticSearchService
                .searchProduct(
                        titleProduct, category, lowerBoundPrice, upperBoundPrice,
                        pageNo, pageSize, sortField, sortDir
                );
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("categories", category);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listProducts", page.getContent());
        model.addAttribute("query", titleProduct);
        return "test-result-search";
    }

//    @GetMapping("/test-search")
//    public String testSearch(Model model){
//
//        List<ProductElasticSearch> productElasticSearches = productElasticSearchRepository
//                .getAllByFuzzyQueryTitleAndPrice(
//                        "laptop", BigDecimal.valueOf(0), BigDecimal.valueOf(100)
//                );
//
//        model.addAttribute("listProducts", productElasticSearches);
//        return "test-s";
//
//    }
}
