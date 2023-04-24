package com.springboot.ecommerce.model.auditListener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AuditListener {
    @PrePersist
    public void setCreatedAt(Object entity) {
        if (entity instanceof BasicEntity) {
            ((BasicEntity) entity).setCreatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void setUpdatedAt(Object entity) {
        if (entity instanceof BasicEntity) {
            ((BasicEntity) entity).setUpdatedAt(LocalDateTime.now());
        }
    }
}
