package com.springboot.ecommerce.search.model.product;


import com.springboot.ecommerce.model.category.Category;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
        productElasticSearch.setTitleKeyword(product.getTitle());
        productElasticSearch.setCategories(new ArrayList<>());
        productElasticSearch.setTags(new ArrayList<>());
        for (Category category: product.getCategories()) {
            productElasticSearch.getCategories().add(category.getId().toString());
        };
        for (Tag tag : product.getTags()){
            productElasticSearch.getTags().add(tag.getId().toString());
        }

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
        productElasticSearch.setTitleKeyword(product.getTitle());
        productElasticSearch.setSlug(product.getSlug());
        productElasticSearch.setImageLink(product.getImageLink());
        productElasticSearch.setPrice(product.getPrice());
        productElasticSearch.setDiscount(product.getDiscount());
        productElasticSearch.setQuantity(product.getQuantity());
        productElasticSearch.setSku(product.getSku());
        List<String> categoriesTitle = new ArrayList<>();
        List<String> tagsTitle = new ArrayList<>();
        for (Category category: product.getCategories()) {
            categoriesTitle.add(category.getId().toString());
        };
        for (Tag tag : product.getTags()){
            tagsTitle.add(tag.getId().toString());
        }
        productElasticSearch.setCategories(categoriesTitle);
        productElasticSearch.setTags(tagsTitle);

        this.save(productElasticSearch);

    }




    @Override
    public Page<ProductElasticSearch> getAllByTitleAndPriceIsBetween(String title, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice,
                                                                     int pageNo, int pageSize,
                                                                     String sortField, String sortDirection) {
        return productElasticSearchRepository
                .getAllByFuzzyQueryTitleAndPriceIsBetween(
                        title, lowerBoundPrice, upperBoundPrice,
                        this.findPaginated(pageNo, pageSize, sortField, sortDirection)
                );
    }

    @Override
    public Page<ProductElasticSearch> getAllByTitleAndCategories(String title, List<String> categories,
                                                                 int pageNo, int pageSize,
                                                                 String sortField, String sortDirection) {
        return productElasticSearchRepository
                .getAllByFuzzyQueryTitleAndCategoriesContaining(
                        title, categories,
                        this.findPaginated(pageNo, pageSize, sortField, sortDirection)
                );
    }


    @Override
    public Page<ProductElasticSearch> getAllByTitleAndCategoriesAndPriceIsBetween(String title, List<String> categories,
                                                                                  BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice,
                                                                                  int pageNo, int pageSize, String sortFiled, String sortDirection) {
        return productElasticSearchRepository.
                getAllByFuzzyQueryTitleAndCategoriesContainingAndPriceIsBetween(
                        title, categories, lowerBoundPrice, upperBoundPrice,
                        this.findPaginated(pageNo, pageSize, sortFiled, sortDirection)
                );
    }


    @Override
    public Page<ProductElasticSearch> getAllByTitle(String title,
                                                    int pageNo, int pageSize,
                                                    String sortField, String sortDirection) {
        return productElasticSearchRepository
                .getAllByFuzzyQueryTitle(
                        title,
                        this.findPaginated(pageNo, pageSize, sortField, sortDirection)
                );
    }

    @Override
    public Pageable findPaginated(int pageNo,
                                  int pageSize,
                                  String sortField,
                                  String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }

    @Override
    public Page<ProductElasticSearch> searchProduct(String title, String categories,
                                                    BigDecimal upperBoundPrice, BigDecimal lowerBoundPrice,
                                                    int pageNo, int pageSize, String sortField, String sortDirection) {
        if (categories == null && upperBoundPrice == null && lowerBoundPrice == null){
            return this.getAllByTitle(title,pageNo, pageSize, sortField, sortDirection);
        } else if (categories == null && upperBoundPrice != null && lowerBoundPrice != null) {
            return this.getAllByTitleAndPriceIsBetween(title, upperBoundPrice, lowerBoundPrice, pageNo, pageSize, sortField, sortDirection);
        } else if (categories != null && upperBoundPrice == null && lowerBoundPrice == null) {
            return this.getAllByTitleAndCategories(title, new ArrayList<>(Arrays.asList(categories)), pageNo, pageSize, sortField, sortDirection);
        } else if (categories != null && upperBoundPrice != null && lowerBoundPrice != null) {
            return this.getAllByTitleAndCategoriesAndPriceIsBetween(title, new ArrayList<>(Arrays.asList(categories)), upperBoundPrice, lowerBoundPrice, pageNo, pageSize, sortField, sortDirection);
        }
        return null;
    }
}
