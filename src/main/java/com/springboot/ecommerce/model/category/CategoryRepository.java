package com.springboot.ecommerce.model.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Transactional
    @Query("select c " +
            "from Category as c " +
            "where c.id not in ?1")
    List<Category> getAllCategoriesExceptId(List<Long> categoriesId);

    @Transactional
    @Modifying
    @Query("select c.id " +
            "from Category as c " +
            "where c.categoryParent.id = ?1")
    List<Long> getAllSubCategoriesOf(Long categoryParentId);

    @Query("select c.categoryParent.id " +
            "from Category as c ")
    List<Long> getALlCategoryParent();
}
