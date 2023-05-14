package com.springboot.ecommerce.search.model.product;


import com.springboot.ecommerce.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductElasticSearchServiceImpl implements ProductElasticSearchService{

    private final ProductElasticSearchRepository productElasticSearchRepository;

    @Override
    public void save(ProductElasticSearch productElasticSearch) {
        productElasticSearchRepository.save(productElasticSearch);
    }

    @Override
    public void updateProductElasticSearch(Product product) {
        ProductElasticSearch  productElasticSearch = this.getProductElasticSearchById(product.getId());
        productElasticSearch.setSlug(product.getSlug());
        productElasticSearch.setImageLink(product.getImageLink());
        productElasticSearch.setPrice(product.getPrice());
        productElasticSearch.setDiscount(product.getDiscount());
        productElasticSearch.setQuantity(product.getQuantity());
        productElasticSearch.setSku(product.getSku());
        productElasticSearch.setTitle(product.getTitle());

        this.save(productElasticSearch);
    }

    @Override
    public ProductElasticSearch getProductElasticSearchById(Integer productId) {
        Optional<ProductElasticSearch> productElasticSearchOptional = productElasticSearchRepository.findById(productId);
        if (productElasticSearchOptional.isPresent()){
            return productElasticSearchOptional.get();
        } else {
            throw new IllegalStateException("ProductElasticSearch not found for id: " + productId);
        }
    }

    @Override
    public void delete(Integer productId) {
        ProductElasticSearch  productElasticSearch = this.getProductElasticSearchById(productId);
        productElasticSearchRepository.delete(productElasticSearch);
    }

    @Override
    public void initProductElasticSearch(Product product) {
        ProductElasticSearch productElasticSearch = new ProductElasticSearch();
        productElasticSearch.setId(product.getId());
        productElasticSearch.setTitle(product.getTitle());
        productElasticSearch.setSlug(product.getSlug());
        productElasticSearch.setImageLink(product.getImageLink());
        productElasticSearch.setPrice(product.getPrice());
        productElasticSearch.setDiscount(product.getDiscount());
        productElasticSearch.setQuantity(product.getQuantity());
        productElasticSearch.setSku(product.getSku());

        this.save(productElasticSearch);

    }


    @Override
    public List<ProductElasticSearch> getAllByTitle(String title) {
        return productElasticSearchRepository.getAllByFuzzyQueryTitle(title);
    }

    @Override
    public List<ProductElasticSearch> getAllByTitleAndPriceIsBetween(String title, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice) {
        return productElasticSearchRepository.getAllByFuzzyQueryTitleAndPriceIsBetween(title, lowerBoundPrice, upperBoundPrice);
    }
}
