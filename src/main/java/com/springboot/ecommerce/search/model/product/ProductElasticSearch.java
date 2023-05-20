package com.springboot.ecommerce.search.model.product;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
public class ProductElasticSearch {
    @Id
    @Field(name = "id", type = FieldType.Keyword)
    private Integer id;

    private String slug;

    @Lob
    private String imageLink;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private String titleKeyword;


    @Field(type = FieldType.Keyword)
    private String sku;

    private BigDecimal price;

    private BigDecimal discount;

    private Long quantity;

    @Field(name = "categories", type = FieldType.Keyword)
    private List<String> categories;

    @Field(name = "tags", type = FieldType.Keyword)
    private List<String> tags;

}
