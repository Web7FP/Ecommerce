package com.springboot.ecommerce.model.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByTags_Id(Long id);

    List<Product> findAllByCategories_Id(Long id);

    Product findByProductMetas_Id(Long productMetaId);
}
