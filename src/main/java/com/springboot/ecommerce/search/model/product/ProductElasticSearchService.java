package com.springboot.ecommerce.search.model.product;

import com.springboot.ecommerce.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductElasticSearchService {

    void save(ProductElasticSearch productElasticSearch);

    void updateProductElasticSearch(Product product);

    ProductElasticSearch getProductElasticSearchById(Integer productId);

    void delete(Integer productId);

    void initProductElasticSearch(Product product);

    Page<ProductElasticSearch> getAllByTitleAndPriceIsBetween(String title,
                                                              BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice,
                                                              int pageNo, int pageSize,
                                                              String sortField, String sortDirection);

    Page<ProductElasticSearch> getAllByTitleAndCategories(String title, List<String> categories,
                                                          int pageNo, int  pageSize,
                                                          String sortFiled, String sortDirection);

    Page<ProductElasticSearch> getAllByTitleAndCategoriesAndPriceIsBetween(String title, List<String> categories,
                                                                           BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice,
                                                                           int pageNo, int pageSize,
                                                                           String sortFiled, String sortDirection);

    Page<ProductElasticSearch> getAllByTitle(String title,
                                             int pageNo, int pageSize,
                                             String sortField, String sortDirection);

    Pageable findPaginated(int pageNo, int pageSize,
                           String sortField, String sortDirection);

    Page<ProductElasticSearch> searchProduct(String title, List<String>  categories,
                                             BigDecimal upperBoundPrice, BigDecimal lowerBoundPrice,
                                             int pageNo, int pageSize,
                                             String sortField, String sortDirection);
}
