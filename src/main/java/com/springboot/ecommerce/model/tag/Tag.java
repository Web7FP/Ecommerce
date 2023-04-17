package com.springboot.ecommerce.model.tag;


import com.springboot.ecommerce.model.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @SequenceGenerator(
            name = "tag_sequence",
            sequenceName = "tag_sequence",
            allocationSize = 5,
            initialValue = 1
    )
    @GeneratedValue(
            generator = "tag_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String metaTitle;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = true, columnDefinition = "TEXT")
    @Lob
    private String content;

    @ManyToMany(mappedBy = "tags")
    private List<Product> products = new ArrayList<>();
}
