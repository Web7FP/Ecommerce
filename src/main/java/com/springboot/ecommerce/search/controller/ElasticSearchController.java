package com.springboot.ecommerce.search.controller;


import com.springboot.ecommerce.search.model.product.ProductElasticSearch;
import com.springboot.ecommerce.search.model.product.ProductElasticSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;


@Controller
@RequiredArgsConstructor
public class ElasticSearchController {

    private final ProductElasticSearchServiceImpl productElasticSearchService;




    @GetMapping("/search/page={pageNo}")
    public String findPaginatedProduct(
            @RequestParam("name") String titleProduct,
            @PathVariable("pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            @RequestParam("category") String category,
            @RequestParam("upperBoundPrice") BigDecimal  upperBoundPrice,
            @RequestParam("lowerBoundPrice") BigDecimal lowerBoundPrice,
            Model model
    ){
        int pageSize = 4;
        Page<ProductElasticSearch> page = productElasticSearchService
                .searchProduct(
                        titleProduct, category, upperBoundPrice, lowerBoundPrice,
                        pageNo, pageSize, sortField, sortDir
                );
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("category", category);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listProducts", page.getContent());
        model.addAttribute("query", titleProduct);
        return "test-result-search";
    }

    @GetMapping("/search")
    public String resultSearch(@RequestParam("q") String titleProduct, Model model){
        return findPaginatedProduct(
                titleProduct, 1, "titleKeyword", "asc",
                "1", null, null, model
        );
    }
}
