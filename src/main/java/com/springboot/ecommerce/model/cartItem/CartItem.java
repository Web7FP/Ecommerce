package com.springboot.ecommerce.model.cartItem;

import com.fasterxml.jackson.annotation.*;
import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.auditListener.AuditListener;
import com.springboot.ecommerce.model.auditListener.BasicEntity;
import com.springboot.ecommerce.model.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditListener.class)
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cart"})
@JsonIdentityInfo(
        scope = CartItem.class,
        generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class CartItem extends BasicEntity {
    @Id
    @SequenceGenerator(
            name = "cart_item_sequence",
            sequenceName = "cart_item_sequence",
            allocationSize = 100,
            initialValue = 1
    )
    @GeneratedValue(
            generator = "cart_item_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;

    private String sku;
    private BigDecimal price;
    private BigDecimal discount;
    private Long quantity = 1L;
    private boolean active;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;


    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
