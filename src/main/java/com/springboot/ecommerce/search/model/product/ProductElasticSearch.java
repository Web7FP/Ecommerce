package com.springboot.ecommerce.search.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
public class ProductElasticSearch {
    @Id
    @Field(name = "id", type = FieldType.Keyword)
    private Integer id;

    @Field(name = "slug", type = FieldType.Text)
    private String slug;


    private String imageLink;

    @Field(name = "title", type = FieldType.Text)
    private String title;

    private String sku;

    private BigDecimal price;

    private BigDecimal discount;

    private Long quantity;

}
