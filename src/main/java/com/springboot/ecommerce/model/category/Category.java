package com.springboot.ecommerce.model.category;


import com.springboot.ecommerce.model.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 5,
            initialValue = 1
    )
    @GeneratedValue(
            generator = "category_sequence",
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

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(
            name = "parentId",
            referencedColumnName = "id")
    private Category categoryParent;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();

}
