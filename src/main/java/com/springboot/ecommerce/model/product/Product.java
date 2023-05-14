package com.springboot.ecommerce.model.product;

import com.fasterxml.jackson.annotation.*;
import com.springboot.ecommerce.model.cartItem.CartItem;
import com.springboot.ecommerce.model.category.Category;
import com.springboot.ecommerce.model.auditListener.AuditListener;
import com.springboot.ecommerce.model.auditListener.BasicEntity;
import com.springboot.ecommerce.model.orderItem.OrderItem;
import com.springboot.ecommerce.model.productMeta.ProductMeta;
import com.springboot.ecommerce.model.tag.Tag;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cartItems"})
@JsonIdentityInfo(
        scope = Product.class,
        generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Product extends BasicEntity {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 5,
            initialValue = 1
    )
    @GeneratedValue(
            generator = "product_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Lob
    private String imageLink;

    @Column(nullable = false)
    private String title;

    private String metaTitle;

    @Column(nullable = false)
    private String slug;

    @Lob
    private String summary;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal discount;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private boolean shop;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt;

    private LocalDateTime startsAt;

    private LocalDateTime endsAt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductMeta> productMetas = new ArrayList<>();


    @ManyToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonIgnore
    private List<Tag> tags = new ArrayList<>();

    @ManyToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

}
