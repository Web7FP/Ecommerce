package com.springboot.ecommerce.search.model.product;


import com.springboot.ecommerce.model.category.Category;
import com.springboot.ecommerce.model.category.CategoryServiceImpl;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.tag.Tag;
import com.springboot.ecommerce.model.tag.TagServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
            productElasticSearch.getCategories().add(category.getTitle());
        }
        for (Tag tag : product.getTags()){
            productElasticSearch.getTags().add(tag.getTitle());
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
            categoriesTitle.add(category.getTitle());
        }
        for (Tag tag : product.getTags()){
            tagsTitle.add(tag.getTitle());
        }
        productElasticSearch.setCategories(categoriesTitle);
        productElasticSearch.setTags(tagsTitle);

        this.save(productElasticSearch);

    }



    @Override
    public Page<ProductElasticSearch> getAllByTitle(String title, Pageable pageable) {
        return productElasticSearchRepository.getAllByFuzzyQueryTitle(
                title, pageable
        );
    }

    @Override
    public Page<ProductElasticSearch> getAllByTitleAndPriceIsBetween(String title,
                                                                     BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice, Pageable pageable) {
        return productElasticSearchRepository.getAllByFuzzyQueryTitleAndPriceIsBetween(
                title, lowerBoundPrice, upperBoundPrice, pageable
        );
    }

    @Override
    public Page<ProductElasticSearch> getAllByTitleAndCategories(String title, List<String> categories, Pageable pageable) {
        return productElasticSearchRepository.getAllByFuzzyQueryTitleAndCategoriesContaining(
                title, categories, pageable
        );
    }

    @Override
    public Page<ProductElasticSearch> getAllByTitleAndTags(String title, List<String> tags, Pageable pageable) {
        return productElasticSearchRepository.getAllByFuzzyQueryTitleAndTags(
                title, tags, pageable
        );
    }

    @Override
    public Page<ProductElasticSearch> getAllByTitleAndCategoriesAndPriceIsBetween(String title, List<String> categories,
                                                                                  BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice, Pageable pageable) {
        return productElasticSearchRepository.getAllByFuzzyQueryTitleAndCategoriesContainingAndPriceIsBetween(
                title, categories, lowerBoundPrice, upperBoundPrice, pageable
        );
    }

    @Override
    public Page<ProductElasticSearch> getAllByTitleAndTagsAndPricesIsBetween(String title, List<String> tags,
                                                                             BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice, Pageable pageable) {
        return productElasticSearchRepository.getAllByFuzzyQueryTitleAndTagsAndPriceIsBetween(
                title, tags, lowerBoundPrice, upperBoundPrice, pageable
        );
    }

    @Override
    public Page<ProductElasticSearch> getAllByTitleAndCategoriesAndTags(String title,
                                                                        List<String> categories, List<String> tags, Pageable pageable) {
        return productElasticSearchRepository.getAllByFuzzyQueryTitleAndCategoriesAndTags(
                title, categories, tags, pageable
        );
    }

    @Override
    public Page<ProductElasticSearch> getAllByTitleAndCategoriesAndTagsAndPriceIsBetween(String title,
                                                                                         List<String> categories, List<String> tags,
                                                                                         BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice, Pageable pageable) {
        return productElasticSearchRepository.getAllByFuzzyQueryTitleAndCategoriesAndTagsAndPriceIsBetween(
                title, categories, tags, lowerBoundPrice, upperBoundPrice, pageable
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
    public Page<ProductElasticSearch> searchProduct(String title,
                                                    List<String> categories, List<String> tags,
                                                    BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice,Pageable pageable) {



        if (categories == null && tags == null && lowerBoundPrice == null && upperBoundPrice == null){
            return this.getAllByTitle(title, pageable);
        } else if (tags == null && lowerBoundPrice == null && upperBoundPrice == null) {
            return this.getAllByTitleAndCategories(title, categories, pageable);
        } else if (categories == null && lowerBoundPrice == null && upperBoundPrice == null) {
            return this.getAllByTitleAndTags(title, tags, pageable);
        } else if (categories == null && tags == null) {
            return this.getAllByTitleAndPriceIsBetween(title, lowerBoundPrice, upperBoundPrice, pageable);
        } else if (categories == null) {
            return this.getAllByTitleAndTagsAndPricesIsBetween(title, tags, lowerBoundPrice, upperBoundPrice, pageable);
        } else if (tags == null) {
            return this.getAllByTitleAndCategoriesAndPriceIsBetween(title, categories, lowerBoundPrice, upperBoundPrice, pageable);
        } else if (lowerBoundPrice == null && upperBoundPrice == null) {
            return this.getAllByTitleAndCategoriesAndTags(title,categories, tags, pageable);
        }
        return this.getAllByTitleAndCategoriesAndTagsAndPriceIsBetween(title, categories, tags, lowerBoundPrice, upperBoundPrice, pageable);
    }


    @Override
    public List<String> getAllCategoriesFromResultSearch(Page<ProductElasticSearch> productElasticSearches) {
        return productElasticSearches.stream()
                .flatMap(productElasticSearch -> productElasticSearch.getCategories().stream())
                .distinct().toList();
    }

    @Override
    public List<String> getAllTagsFromResultSearch(Page<ProductElasticSearch> productElasticSearches) {
        return productElasticSearches.stream()
                .flatMap(productElasticSearch -> productElasticSearch.getTags().stream())
                .distinct().toList();
    }


    @Override
    public void setFilterAttributeSession(HttpSession session, String keyword, List<String> categories, List<String> tags, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice) {

        String oldKeyword = (String) session.getAttribute("keywordSearch");
        if (!keyword.equals(oldKeyword)){
            Page<ProductElasticSearch> productElasticSearchPage = this.getAllProductsFromResultSearch(keyword, categories, tags, lowerBoundPrice, upperBoundPrice);
            session.setAttribute("keywordSearch", keyword);
            session.setAttribute("categoriesFilter", this.getAllCategoriesFromResultSearch(productElasticSearchPage));
            session.setAttribute("tagsFilter", this.getAllTagsFromResultSearch(productElasticSearchPage));

        }
    }

    @Override
    public List<String> getCategoriesFilterFromSession(HttpSession session) {
        return (List<String>) session.getAttribute("categoriesFilter");
    }

    @Override
    public List<String> getTagsFilterFromSession(HttpSession session) {
        return (List<String>) session.getAttribute("tagsFilter");
    }

    @Override
    public Page<ProductElasticSearch> getAllProductsFromResultSearch(String title, List<String> categories, List<String> tags, BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice) {
        Pageable wholePage = Pageable.unpaged();
        return this.searchProduct(title, categories, tags, lowerBoundPrice, upperBoundPrice, wholePage);
    }
}

