package com.springboot.ecommerce.model.productMeta;


import java.util.List;

public interface ProductMetaService {
    void saveProductMeta(ProductMeta productMeta);

    void deleteByProduct(Integer productId);

    List<ProductMeta> getAllByProduct(Integer productId);

    void deleteProductMeta(Long productMetaId);

    ProductMeta getProductMetaById(Long id);

}
