package com.springboot.ecommerce.model.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByTags_Id(Long id);

    List<Product> findAllByCategories_Id(Long id);

    List<Product> findAllByCategories_IdAndTags_Id(Long categoryId, Long tagId);

    Product findByProductMetas_Id(Long productMetaId);

    @Query("select p " +
            "from Product as p " +
            "where p.slug = :slugProduct")
    Product findBySlugProduct(String slugProduct);


    @Query("select p " +
            "from Product as p join p.categories as c " +
            "where c.title = :categoryName")
    Page<Product> getAllProductsByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

    @Query("select p " +
            "from Product as p join p.categories as c " +
            "where c.slug = :categorySlug")
    Page<Product> getAllProductsByCategoryId(@Param("categorySlug") String categorySlug, Pageable pageable);


    @Query("select p " +
            "from Product as p join p.tags as t " +
            "where t.slug = :tagSlug")
    Page<Product> getAllProductByTagId(@Param("tagSlug") String tagSlug, Pageable pageable);

    @Query("select distinct p " +
            "from Product as p join p.categories as c " +
            "where c.id in ?1 and p.id <> ?2")
    Page<Product> getAllRelatedProduct(List<Long> relatedCategoriesId, Integer productId, Pageable pageable);


}
