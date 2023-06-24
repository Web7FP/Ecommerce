package com.springboot.ecommerce.search.model.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductElasticSearchRepository extends ElasticsearchRepository<ProductElasticSearch, Integer> {

    /**
     * Tìm kiếm dựa trên tên sản phẩm (fuzzy query)
     *
     * @param title (tên sản phẩm)
     * @param pageable (object chứa các thuộc tính liên quan đến sort và pagination)
     * @return Products
     */
    @Query("{\"bool\" : { " +
            "   \"must\":[" +
            "       {\"match\":{" +
            "           \"title\":{" +
            "               \"query\":\"?0\", " +
            "               \"fuzziness\": 2," +
            "               \"prefix_length\": 2," +
            "               \"max_expansions\": 50" +
            "           }}" +
            "       }" +
            "   ]" +
            "}}")
    Page<ProductElasticSearch> getAllByFuzzyQueryTitle(String title, Pageable pageable);


    /**
     * Tìm kiếm dựa trên tên sản phẩm và có giá trong khoảng đầu vào.
     * @param title (tên sp)
     * @param lowerBoundPrice (giới hạn dưới của giá)
     * @param upperBoundPrice (giới hạn trên của giá)
     * @param pageable (object chứa các thuộc tính liên quan đến sort và pagination)
     * @return Products
     */
    @Query("{\"bool\": { " +
            "   \"must\": [ " +
            "       {\"match\": { " +
            "           \"title\": { " +
            "               \"query\": \"?0\", " +
            "               \"fuzziness\": 2, " +
            "               \"prefix_length\": 2, " +
            "               \"max_expansions\": 50 " +
            "           }}" +
            "       }, " +
            "       {\"range\": { " +
            "           \"price\": { " +
            "               \"gte\": \"?1\"," +
            "               \"lte\": \"?2\" " +
            "           }}" +
            "       }" +
            "   ]}" +
            "}}")
    Page<ProductElasticSearch> getAllByFuzzyQueryTitleAndPriceIsBetween(String title,
                                                                        BigDecimal lowerBoundPrice,
                                                                        BigDecimal upperBoundPrice, Pageable pageable);


    /**
     * Tìm kiếm dựa trên tên sản phẩm và có chứa tất cả category đầu vào.
     * @param titleProduct (tên sp)
     * @param categoriesTitle (tên các category đầu vào)
     * @param pageable (object chứa các thuộc tính liên quan đến sort và pagination)     * @return Products
     */
    @Query("{ \"bool\" : { " +
            "   \"filter\" : [{" +
            "        \"script\" : { " +
            "           \"script\" : { " +
            "               \"source\" : " +
            "                   \"if (doc['categories'] instanceof List){ " +
            "                       List categories = new ArrayList(doc['categories']); " +
            "                       List searchCategories = params['categories'] ; " +
            "                       int count = 0; " +
            "                       for (int i = 0; i < searchCategories.length; i++){ " +
            "                           if (categories.contains(searchCategories[i])){ " +
            "                               count++; " +
            "                           } " +
            "                       } " +
            "                       if (count == searchCategories.length){" +
            "                           return true;" +
            "                       } else {" +
            "                           return false;" +
            "                       } " +
            "                   } " +
            "                   return false; \", \"params\" : { \"categories\" : ?1} " +
            "           } " +
            "       } " +
            "   }] , " +
            "   \"must\" : [{" +
            "       \"match\" : {" +
            "           \"title\" : {" +
            "               \"query\" : \"?0\" , " +
            "               \"fuzziness\": 2, " +
            "               \"prefix_length\": 2, " +
            "               \"max_expansions\": 10" +
            "           } " +
            "       } " +
            "   }]  " +
            "}}")
    Page<ProductElasticSearch> getAllByFuzzyQueryTitleAndCategoriesContaining(String titleProduct, List<String> categoriesTitle, Pageable pageable);


    /**
     * Tìm kiếm dựa trên tên sản phẩm và có chứa tất cả tag đầu vào.
     * @param titleProduct (tên sp)
     * @param tagsTitle (tên các tag đầu vào)
     * @param pageable (object chứa các thuộc tính liên quan đến sort và pagination)
     * @return Products
     */
    @Query("{ \"bool\" : { " +
            "   \"filter\" : [{" +
            "        \"script\" : { " +
            "           \"script\" : { " +
            "               \"source\" : " +
            "                   \"if (doc['tags'] instanceof List){ " +
            "                       List tags = new ArrayList(doc['tags']); " +
            "                       List searchTags = params['tags'] ; " +
            "                       int count = 0; " +
            "                       for (int i = 0; i < searchTags.length; i++){ " +
            "                           if (tags.contains(searchTags[i])){ " +
            "                               count++; " +
            "                           } " +
            "                       } " +
            "                       if (count == searchTags.length){" +
            "                           return true;" +
            "                       } else {" +
            "                           return false;" +
            "                       } " +
            "                   } " +
            "                   return false; \", \"params\" : { \"tags\" : ?1} " +
            "           } " +
            "       } " +
            "   }] , " +
            "   \"must\" : [{" +
            "       \"match\" : {" +
            "           \"title\" : {" +
            "               \"query\" : \"?0\" , " +
            "               \"fuzziness\": 2, " +
            "               \"prefix_length\": 2, " +
            "               \"max_expansions\": 10" +
            "           } " +
            "       } " +
            "   }]  " +
            "}}")
    Page<ProductElasticSearch> getAllByFuzzyQueryTitleAndTags(String titleProduct, List<String> tagsTitle, Pageable pageable);


    /**
     * Tìm kiếm dựa trên tên sản phẩm với điều kiện price và categories.
     * @param titleProduct (tên sản phẩm)
     * @param categoriesTitle (tên các category đầu vào)
     * @param lowerBoundPrice (giới hạn dưới của giá)
     * @param upperBoundPrice (giới hạn trên của giá)
     * @param pageable (object chứa các thuộc tính liên quan đến sort và pagination)
     * @return Products
     */
    @Query("{ \"bool\" : { " +
            "   \"filter\" : [{" +
            "        \"script\" : { " +
            "           \"script\" : { " +
            "               \"source\" : " +
            "                   \"if (doc['categories'] instanceof List){ " +
            "                       List categories = new ArrayList(doc['categories']); " +
            "                       List searchCategories = params['categories'] ; " +
            "                       int count = 0; " +
            "                       for (int i = 0; i < searchCategories.length; i++){ " +
            "                           if (categories.contains(searchCategories[i])){ " +
            "                               count++; " +
            "                           } " +
            "                       } " +
            "                       if (count == searchCategories.length){" +
            "                           return true;" +
            "                       } else {" +
            "                           return false;" +
            "                       } " +
            "                   } " +
            "                   return false; \", " +
            "               \"params\" : { \"categories\" : ?1} " +
            "           } " +
            "       } " +
            "   }] , " +
            "   \"must\" : [" +
            "       {\"match\" : {" +
            "           \"title\" : {" +
            "               \"query\" : \"?0\"," +
            "               \"fuzziness\": 2," +
            "               \"prefix_length\": 2," +
            "               \"max_expansions\": 10" +
            "           }" +
            "       }}," +
            "       {\"range\" : {" +
            "           \"price\" : {" +
            "               \"gte\" : \"?2\"," +
            "               \"lte\" : \"?3\"" +
            "           }" +
            "       }}" +
            "   ]" +
            "}}")
    Page<ProductElasticSearch> getAllByFuzzyQueryTitleAndCategoriesContainingAndPriceIsBetween(String titleProduct, List<String> categoriesTitle,
                                                                                               BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice
                                                                                               , Pageable pageable);

    /**
     * Tìm kiếm dựa trên tên sản phẩm với điều kiện price và tags.
     * @param titleProduct (tên sản phẩm)
     * @param tagsTitle (tên các tag đầu vào)
     * @param lowerBoundPrice (giới hạn dưới của giá)
     * @param upperBoundPrice (giới hạn trên của giá)
     * @param pageable (object chứa các thuộc tính liên quan đến sort và pagination)
     * @return Products
     */
    @Query("{ \"bool\" : { " +
            "   \"filter\" : [{" +
            "        \"script\" : { " +
            "           \"script\" : { " +
            "               \"source\" : " +
            "                   \"if (doc['tags'] instanceof List){ " +
            "                       List tags = new ArrayList(doc['tags']); " +
            "                       List searchTags = params['tags'] ; " +
            "                       int count = 0; " +
            "                       for (int i = 0; i < searchTags.length; i++){ " +
            "                           if (tags.contains(searchTags[i])){ " +
            "                               count++; " +
            "                           } " +
            "                       } " +
            "                       if (count == searchTags.length){" +
            "                           return true;" +
            "                       } else {" +
            "                           return false;" +
            "                       } " +
            "                   } " +
            "                   return false; \", " +
            "               \"params\" : { \"tags\" : ?1} " +
            "           } " +
            "       } " +
            "   }] , " +
            "   \"must\" : [" +
            "       {\"match\" : {" +
            "           \"title\" : {" +
            "               \"query\" : \"?0\"," +
            "               \"fuzziness\": 2," +
            "               \"prefix_length\": 2," +
            "               \"max_expansions\": 10" +
            "           }" +
            "       }}," +
            "       {\"range\" : {" +
            "           \"price\" : {" +
            "               \"gte\" : \"?2\"," +
            "               \"lte\" : \"?3\"" +
            "           }" +
            "       }}" +
            "   ]" +
            "}}")
    Page<ProductElasticSearch> getAllByFuzzyQueryTitleAndTagsAndPriceIsBetween(String titleProduct, List<String> tagsTitle,
                                                                               BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice, Pageable pageable);

    /**
     * Tìm kiếm dựa trên tên sản phẩm với điều kiện tags và categories.
     * @param titleProduct (tên sản phẩm)
     * @param categoriesTitle (tên các category đầu vào)
     * @param tagsTitle (tên các tag đầu vào)
     * @param pageable (object chứa các thuộc tính liên quan đến sort và pagination)
     * @return Products
     */
    @Query("{ \"bool\" : { " +
            "   \"filter\" : [{" +
            "        \"script\" : { " +
            "           \"script\" : { " +
            "               \"source\" : " +
            "                   \"if (doc['categories'] instanceof List){ " +
            "                       List categories = new ArrayList(doc['categories']); " +
            "                       List searchCategories = params['categories'] ; " +
            "                       int count = 0; " +
            "                       for (int i = 0; i < searchCategories.length; i++){ " +
            "                           if (categories.contains(searchCategories[i])){ " +
            "                               count++; " +
            "                           } " +
            "                       } " +
            "                       if (count == searchCategories.length){" +
            "                           return true;" +
            "                       } else {" +
            "                           return false;" +
            "                       } " +
            "                   } " +
            "                   return false; \", " +
            "               \"params\" : { \"categories\" : ?1} " +
            "           } " +
            "       } " +
            "   }, {" +
            "       \"script\" : {" +
            "           \"script\":{" +
            "               \"source\" : " +
            "                   \"if (doc['tags'] instanceof List){" +
            "                       List tags = new ArrayList(doc['tags']); " +
            "                       List searchTags = params['tags']; " +
            "                       int count = 0; " +
            "                       for(int i=0; i< searchTags.length; i++) {" +
            "                           if (tags.contains(searchTags[i])){" +
            "                               count++;" +
            "                           }" +
            "                       } if (count == searchTags.length){" +
            "                           return true;" +
            "                       } else {" +
            "                           return false;" +
            "                       }" +
            "                   } return false;\", " +
            "               \"params\" : {\"tags\" : ?2} " +
            "           } " +
            "       } " +
            "   }] , " +
            "   \"must\" : [" +
            "       {\"match\" : {" +
            "           \"title\" : {" +
            "               \"query\" : \"?0\"," +
            "               \"fuzziness\": 2," +
            "               \"prefix_length\": 2," +
            "               \"max_expansions\": 10" +
            "           }" +
            "       }}" +
            "   ]" +
            "}}")
    Page<ProductElasticSearch> getAllByFuzzyQueryTitleAndCategoriesAndTags(String titleProduct,
                                                                           List<String> categoriesTitle,
                                                                           List<String> tagsTitle, Pageable pageable);



    /**
     * Tìm kiếm dựa trên tên sản phẩm với điều kiện price và categories và tags.
     * @param titleProduct (tên sản phẩm)
     * @param categoriesTitle (tên các category đầu vào)
     * @param tagsTitle (tên các tag đầu vào)
     * @param lowerBoundPrice (giới hạn dưới của giá)
     * @param upperBoundPrice (giới hạn trên của giá)
     * @param pageable (object chứa các thuộc tính liên quan đến sort và pagination)
     * @return Products
     */
    @Query("{ \"bool\" : { " +
            "   \"filter\" : [{" +
            "        \"script\" : { " +
            "           \"script\" : { " +
            "               \"source\" : " +
            "                   \"if (doc['categories'] instanceof List){ " +
            "                       List categories = new ArrayList(doc['categories']); " +
            "                       List searchCategories = params['categories'] ; " +
            "                       int count = 0; " +
            "                       for (int i = 0; i < searchCategories.length; i++){ " +
            "                           if (categories.contains(searchCategories[i])){ " +
            "                               count++; " +
            "                           } " +
            "                       } " +
            "                       if (count == searchCategories.length){" +
            "                           return true;" +
            "                       } else {" +
            "                           return false;" +
            "                       } " +
            "                   } " +
            "                   return false; \", " +
            "               \"params\" : { \"categories\" : ?1} " +
            "           } " +
            "       } " +
            "   }, {" +
            "       \"script\" : {" +
            "           \"script\":{" +
            "               \"source\" : " +
            "                   \"if (doc['tags'] instanceof List){" +
            "                       List tags = new ArrayList(doc['tags']); " +
            "                       List searchTags = params['tags']; " +
            "                       int count = 0; " +
            "                       for(int i=0; i< searchTags.length; i++) {" +
            "                           if (tags.contains(searchTags[i])){" +
            "                               count++;" +
            "                           }" +
            "                       } if (count == searchTags.length){" +
            "                           return true;" +
            "                       } else {" +
            "                           return false;" +
            "                       }" +
            "                   } return false;\", " +
            "               \"params\" : {\"tags\" : ?2} " +
            "           } " +
            "       } " +
            "   }] , " +
            "   \"must\" : [" +
            "       {\"match\" : {" +
            "           \"title\" : {" +
            "               \"query\" : \"?0\"," +
            "               \"fuzziness\": 2," +
            "               \"prefix_length\": 2," +
            "               \"max_expansions\": 10" +
            "           }" +
            "       }}," +
            "       {\"range\" : {" +
            "           \"price\" : {" +
            "               \"gte\" : \"?3\"," +
            "               \"lte\" : \"?4\"" +
            "           }" +
            "       }}" +
            "   ]" +
            "}}")
    Page<ProductElasticSearch> getAllByFuzzyQueryTitleAndCategoriesAndTagsAndPriceIsBetween(String titleProduct,
                                                                                            List<String> categoriesTitle, List<String> tagsTitle,
                                                                                            BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice, Pageable pageable);


}
