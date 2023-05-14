package com.springboot.ecommerce.search.model.product;

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
    List<ProductElasticSearch> getAllByFuzzyQueryTitle(String title);



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
    List<ProductElasticSearch> getAllByFuzzyQueryTitleAndPriceIsBetween(String title,
                                                                        BigDecimal lowerBoundPrice,
                                                                        BigDecimal upperBoundPrice);
}
