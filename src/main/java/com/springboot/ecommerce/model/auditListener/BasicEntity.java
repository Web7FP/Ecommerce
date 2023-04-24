package com.springboot.ecommerce.model.auditListener;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
