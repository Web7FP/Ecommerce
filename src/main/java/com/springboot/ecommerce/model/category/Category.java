package com.springboot.ecommerce.model.category;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
    private String content;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "parentId",
            referencedColumnName = "id")
    private Category categoryParent;

}
