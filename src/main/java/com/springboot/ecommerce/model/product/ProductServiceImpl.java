package com.springboot.ecommerce.model.product;

import com.springboot.ecommerce.model.productMeta.ProductMeta;
import com.springboot.ecommerce.model.productMeta.ProductMetaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMetaServiceImpl productMetaService;

    @Override
    public void saveNewProduct(Product product) {
        for (ProductMeta productMeta: product.getProductMetas()) {
            productMeta.setProduct(product);
            productMetaService.saveProductMeta(productMeta);
        }
        productRepository.save(product);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getALlProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Integer id) {
        productMetaService.deleteByProduct(id);
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAllByTag(Long id) {
        return productRepository.findAllByTags_Id(id);
    }

    @Override
    public List<Product> findAllByCategory(Long id) {
        return productRepository.findAllByCategories_Id(id);
    }

    @Override
    public Product getProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        } else {
            throw new IllegalStateException("Product not found for id: "+ id);
        }
    }

    @Override
    public Product getProductByProductMeta(Long productMetaId) {
        return productRepository.findByProductMetas_Id(productMetaId);
    }

    @Override
    public Page<Product> findPaginated(int pageNo,
                                       int pageSize,
                                       String sortField,
                                       String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Product findBySlugProduct(String slugProduct) {
        return productRepository.findBySlugProduct(slugProduct);
    }


    @Override
    public List<Product> findAllByCategoryAndTag(Long categoryId, Long tagId) {
        return productRepository.findAllByCategories_IdAndTags_Id(categoryId, tagId);
    }
}
