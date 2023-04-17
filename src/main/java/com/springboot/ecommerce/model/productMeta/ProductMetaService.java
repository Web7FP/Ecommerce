package com.springboot.ecommerce.model.productMeta;


import java.util.List;

public interface ProductMetaService {
    void saveProductMeta(ProductMeta productMeta);

    void deleteByProduct(Long productId);

    List<ProductMeta> getAllByProduct(Long productId);

    void deleteProductMeta(Long productMetaId);

    ProductMeta getProductMetaById(Long id);
}
