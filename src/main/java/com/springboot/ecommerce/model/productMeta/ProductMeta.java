package com.springboot.ecommerce.model.productMeta;


import com.springboot.ecommerce.model.auditListener.AuditListener;
import com.springboot.ecommerce.model.auditListener.BasicEntity;
import com.springboot.ecommerce.model.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@EntityListeners(AuditListener.class)
public class ProductMeta extends BasicEntity {
    @Id
    @SequenceGenerator(
            name = "productMetas_sequence",
            sequenceName = "productMetas_sequence",
            allocationSize = 5,
            initialValue = 1
    )
    @GeneratedValue(
            generator = "productMetas_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private String keyProductMeta;

    @Lob
    private String content;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
