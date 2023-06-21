package com.springboot.ecommerce.model.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    void saveNewProduct(Product product);
    Page<Product> getAllProducts(int pageNo, int pageSize, String sortField, String sortDirection);

    void deleteProduct(Integer id);

    List<Product> findAllByTag(Long id);

    List<Product> findAllByCategory(Long id);

    List<Product> findAllByCategoryAndTag(Long categoryId, Long tagId);

    Product getProductById(Integer id);

    Product getProductByProductMeta(Long productMetaId);

    void saveProduct(Product product);

    Pageable findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Product findBySlugProduct (String slugProduct);

    Page<Product> getAllProductByCategoryName(String categoryName, Pageable pageable);

    Page<Product> getAllProductByCategorySlug(String categorySlug, Pageable pageable);

    Page<Product> getAllProductByTagSlug(String tagSlug, Pageable pageable);

    Page<Product> getAllRelatedProduct(Product product, Pageable pageable);
}
