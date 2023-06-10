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
                                                                        BigDecimal upperBoundPrice,
                                                                        Pageable pageable);

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
    Page<ProductElasticSearch> getAllByFuzzyQueryTitleAndCategoriesContainingAndPriceIsBetween(String titleProduct,
                                                                                               List<String> categoriesTitle,
                                                                                               BigDecimal lowerBoundPrice,
                                                                                               BigDecimal upperBoundPrice,
                                                                                               Pageable pageable);



}
