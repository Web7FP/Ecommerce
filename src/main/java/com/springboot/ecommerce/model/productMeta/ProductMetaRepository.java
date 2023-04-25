package com.springboot.ecommerce.model.productMeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductMetaRepository extends JpaRepository<ProductMeta, Long> {

    @Transactional
    @Query("select pm " +
            "from ProductMeta as pm " +
            "where pm.product.id = ?1")
    List<ProductMeta> findByProductId(Integer id);
}
