package com.springboot.ecommerce.model.cart;


import com.fasterxml.jackson.annotation.*;
import com.springboot.ecommerce.model.cartItem.CartItem;
import com.springboot.ecommerce.model.auditListener.AuditListener;
import com.springboot.ecommerce.model.auditListener.BasicEntity;
import com.springboot.ecommerce.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditListener.class)
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cart"})
public class Cart extends BasicEntity {
    @Id
    @SequenceGenerator(
            name = "cart_sequence",
            sequenceName = "cart_sequence",
            allocationSize = 5,
            initialValue = 1
    )
    @GeneratedValue(
            generator = "cart_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus = CartStatus.ACTIVE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private BigDecimal subTotal;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = true)
    private String content;

}
