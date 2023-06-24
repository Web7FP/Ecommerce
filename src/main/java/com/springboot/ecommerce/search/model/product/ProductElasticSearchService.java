package com.springboot.ecommerce.search.model.product;

import com.springboot.ecommerce.model.category.Category;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.tag.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface ProductElasticSearchService {

    void save(ProductElasticSearch productElasticSearch);

    void updateProductElasticSearch(Product product);

    ProductElasticSearch getProductElasticSearchById(Integer productId);

    void delete(Integer productId);

    void initProductElasticSearch(Product product);


    Pageable findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<ProductElasticSearch> searchProduct(String title,
                                             List<String>  categories, List<String> tags,
                                             BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice,
                                             Pageable pageable);


    Page<ProductElasticSearch> getAllByTitle(String title, Pageable pageable);

    Page<ProductElasticSearch> getAllByTitleAndPriceIsBetween(String title, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice, Pageable pageable);

    Page<ProductElasticSearch> getAllByTitleAndCategories(String title, List<String> categories, Pageable pageable);

    Page<ProductElasticSearch> getAllByTitleAndTags(String title, List<String> tags, Pageable pageable);

    Page<ProductElasticSearch> getAllByTitleAndCategoriesAndPriceIsBetween(String title, List<String> categories, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice, Pageable pageable);

    Page<ProductElasticSearch> getAllByTitleAndTagsAndPricesIsBetween(String title, List<String> tags, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice, Pageable pageable);

    Page<ProductElasticSearch> getAllByTitleAndCategoriesAndTags(String title, List<String> categories, List<String> tags, Pageable pageable);

    Page<ProductElasticSearch> getAllByTitleAndCategoriesAndTagsAndPriceIsBetween(String title, List<String> categories, List<String> tags, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice, Pageable pageable);

    List<String> getAllCategoriesFromResultSearch(Page<ProductElasticSearch> productElasticSearches);

    List<String> getAllTagsFromResultSearch(Page<ProductElasticSearch> productElasticSearches);

    void setFilterAttributeSession(HttpSession session,  String keyword, List<String> categories, List<String> tags, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice);

    List<String> getCategoriesFilterFromSession(HttpSession session);

    List<String> getTagsFilterFromSession(HttpSession session);

    Page<ProductElasticSearch> getAllProductsFromResultSearch(String title, List<String> categories, List<String> tags, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice);


}
