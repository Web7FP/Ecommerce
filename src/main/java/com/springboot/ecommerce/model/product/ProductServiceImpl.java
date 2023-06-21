package com.springboot.ecommerce.model.product;

import com.springboot.ecommerce.model.category.Category;
import com.springboot.ecommerce.model.productMeta.ProductMeta;
import com.springboot.ecommerce.model.productMeta.ProductMetaServiceImpl;
import com.springboot.ecommerce.search.model.product.ProductElasticSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMetaServiceImpl productMetaService;
    private final ProductElasticSearchServiceImpl productElasticSearchService;


    @Override
    public void saveNewProduct(Product product) {
        for (ProductMeta productMeta: product.getProductMetas()) {
            productMeta.setProduct(product);
            productMetaService.saveProductMeta(productMeta);
        }
        productRepository.save(product);
        productElasticSearchService.initProductElasticSearch(product);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
        productElasticSearchService.updateProductElasticSearch(product);
    }

    @Override
    public Page<Product> getAllProducts(int pageNo, int pageSize, String sortField, String sortDirection) {
        return productRepository.findAll(
                this.findPaginated(pageNo, pageSize, sortField, sortDirection)
        );

    }

    @Override
    public void deleteProduct(Integer id) {
        productElasticSearchService.delete(id);
        productMetaService.deleteByProduct(id);
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAllByTag(Long id) {
        return productRepository.findAllByTags_Id(id);
    }

    @Override
    public List<Product> findAllByCategory(Long id) {
        return productRepository.findAllByCategories_Id(id);
    }

    @Override
    public Product getProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        } else {
            throw new IllegalStateException("Product not found for id: "+ id);
        }
    }

    @Override
    public Product getProductByProductMeta(Long productMetaId) {
        return productRepository.findByProductMetas_Id(productMetaId);
    }

    @Override
    public Pageable findPaginated(int pageNo,
                                       int pageSize,
                                       String sortField,
                                       String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        return  PageRequest.of(pageNo - 1, pageSize, sort);
    }

    @Override
    public Product findBySlugProduct(String slugProduct) {
        return productRepository.findBySlugProduct(slugProduct);
    }


    @Override
    public List<Product> findAllByCategoryAndTag(Long categoryId, Long tagId) {
        return productRepository.findAllByCategories_IdAndTags_Id(categoryId, tagId);
    }


    @Override
    public Page<Product> getAllProductByCategoryName(String categoryName, Pageable pageable) {
        return productRepository.getAllProductsByCategoryName(categoryName,pageable);
    }

    @Override
    public Page<Product> getAllProductByCategorySlug(String categorySlug, Pageable pageable) {
        return productRepository.getAllProductsByCategoryId(categorySlug, pageable);
    }


    @Override
    public Page<Product> getAllProductByTagSlug(String tagSLug, Pageable pageable) {
        return productRepository.getAllProductByTagId(tagSLug, pageable);
    }


    @Override
    public Page<Product> getAllRelatedProduct(Product product, Pageable pageable) {
        List<Long> relatedCategoriesId = product.getCategories()
                .stream()
                .map(Category::getId)
                .toList();
        return productRepository.getAllRelatedProduct(relatedCategoriesId, product.getId(), pageable);
    }
}

