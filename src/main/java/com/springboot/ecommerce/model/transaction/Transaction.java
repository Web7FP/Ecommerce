package com.springboot.ecommerce.model.transaction;


import com.springboot.ecommerce.model.auditListener.AuditListener;
import com.springboot.ecommerce.model.auditListener.BasicEntity;
import com.springboot.ecommerce.model.order.Order;
import com.springboot.ecommerce.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

import static com.springboot.ecommerce.model.transaction.TransactionStatus.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditListener.class)
@DynamicUpdate
@Table(name = "transactions")
public class Transaction extends BasicEntity {
    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 5,
            initialValue = 1
    )
    @GeneratedValue(
            generator = "transaction_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status = PENDING;

    @Enumerated(EnumType.STRING)
    private TransactionMode mode;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "order_id")
    private Order order;

}
