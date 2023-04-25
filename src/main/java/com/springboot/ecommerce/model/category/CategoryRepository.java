package com.springboot.ecommerce.model.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Transactional
    @Query("select c " +
            "from Category as c " +
            "where c.id <> ?1")
    List<Category> getAllCategoriesExceptId(Long id);

}