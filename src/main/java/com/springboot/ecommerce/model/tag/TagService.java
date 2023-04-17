package com.springboot.ecommerce.model.tag;


import java.util.List;

public interface TagService {
    void saveProductTag(Tag tag);

    List<Tag> getAllProductTags();

    void deleteTag(Long id);

    Tag getTagById(Long id);
}
