package com.springboot.ecommerce.model.tag;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    void saveProductTag(Tag tag);

    List<Tag> getAllProductTags();

    void deleteTag(Long id);

    Tag getTagById(Long id);

    Pageable findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Tag> getAllTagWithPaginationAndSort(int pageNo, int pageSize, String sortField, String sortDirection);

}
