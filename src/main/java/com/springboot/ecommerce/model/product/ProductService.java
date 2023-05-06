package com.springboot.ecommerce.model.product;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    void saveNewProduct(Product product);
    List<Product> getAllProducts();

    void deleteProduct(Integer id);

    List<Product> findAllByTag(Long id);

    List<Product> findAllByCategory(Long id);

    List<Product> findAllByCategoryAndTag(Long categoryId, Long tagId);

    Product getProductById(Integer id);

    Product getProductByProductMeta(Long productMetaId);

    void saveProduct(Product product);

    Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Product findBySlugProduct (String slugProduct);
}
