package com.springboot.ecommerce.search.model.product;

import com.springboot.ecommerce.model.product.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductElasticSearchService {

    void save(ProductElasticSearch productElasticSearch);

    void updateProductElasticSearch(Product product);

    ProductElasticSearch getProductElasticSearchById(Integer productId);

    void delete(Integer productId);

    void initProductElasticSearch(Product product);

    List<ProductElasticSearch> getAllByTitle(String title);

    List<ProductElasticSearch> getAllByTitleAndPriceIsBetween(String title, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice);
}
