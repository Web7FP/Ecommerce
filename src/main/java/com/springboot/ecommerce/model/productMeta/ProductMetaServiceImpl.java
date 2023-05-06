package com.springboot.ecommerce.model.productMeta;

import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductMetaServiceImpl implements ProductMetaService {
    private final ProductMetaRepository productMetaRepository;

    @Override
    public void saveProductMeta(ProductMeta productMeta) {
        productMetaRepository.save(productMeta);
    }


    @Override
    public List<ProductMeta> getAllByProduct(Integer productId) {
        return productMetaRepository.findByProductId(productId);
    }

    @Override
    public void deleteByProduct(Integer productId) {
        List<ProductMeta> productMetas = this.getAllByProduct(productId);
        for (ProductMeta productMeta : productMetas){
            productMetaRepository.deleteById(productMeta.getId());
        }
    }

    @Override
    public ProductMeta getProductMetaById(Long id) {
        Optional<ProductMeta> optionalProductMeta = productMetaRepository.findById(id);
        if (optionalProductMeta.isPresent()){
            return optionalProductMeta.get();
        } else {
            throw new IllegalStateException("Product Meta not found for : "+id);
        }
    }

    @Override
    public void deleteProductMeta(Long productMetaId) {
        ProductMeta productMeta = this.getProductMetaById(productMetaId);
        productMeta.setProduct(null);
        this.saveProductMeta(productMeta);
        productMetaRepository.delete(productMeta);
    }
}
