package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.model.category.CategoryServiceImpl;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.product.ProductServiceImpl;
import com.springboot.ecommerce.model.productMeta.ProductMeta;
import com.springboot.ecommerce.model.productMeta.ProductMetaServiceImpl;
import com.springboot.ecommerce.model.tag.TagServiceImpl;
import com.springboot.ecommerce.model.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product-management")
public class ProductController {

    private final CategoryServiceImpl categoryService;
    private final TagServiceImpl tagService;
    private final ProductServiceImpl productService;
    private final ProductMetaServiceImpl productMetaService;
    private final UserService userService;

    @GetMapping("products-list")
    public String viewProductList(Model model){
        return findPaginated(1, "title", "asc", model);
    }

    @GetMapping("products-list/page/{pageNo}")
    public String findPaginated(@PathVariable("pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model  model
    ){
        int pageSize = 5;
        Page<Product> page = productService.getAllProducts(pageNo, pageSize, sortField, sortDir);
        List<Product> listProducts = page.getContent();

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("currentPage", pageNo);
        return "management-product";

    }

    @GetMapping("add-new-product.html")
    public String getAddNewProductPage(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("categoriesList", categoryService.getAllCategories());
        model.addAttribute("productTags", tagService.getAllProductTags());
        return "update-product-form";
    }

    @PostMapping("save-product")
    public String saveNewProduct(@ModelAttribute("product") Product product,
                                 @AuthenticationPrincipal UserDetails currentUser){
        product.setUser(userService.findByEmail(currentUser.getUsername()));
        productService.saveNewProduct(product);
        return "redirect:/product-management/products-list";
    }

    @GetMapping("delete-product/{id}")
    public String deleteProduct(@PathVariable(value = "id") Integer id){
        productService.deleteProduct(id);
        return "redirect:/product-management/products-list";

    }


    @GetMapping("update-product-form/{id}")
    public String updateProduct(@PathVariable("id") Integer id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categoriesList", categoryService.getAllCategories());
        model.addAttribute("productTags", tagService.getAllProductTags());
        return "update-product-form";
    }

    @GetMapping("product-meta-management/{id}")
    public String viewProductMetaList(@PathVariable("id") Integer productId, Model model){
        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("productMetaList", productMetaService.getAllByProduct(productId));
        return "management-product-meta";
    }

    @GetMapping("delete-product-meta/{id}")
    public String deleteProductMeta(@PathVariable("id") Long productMetaId,
                                    RedirectAttributes redirectAttributes){
        Product product = productService.getProductByProductMeta(productMetaId);
        product.getProductMetas().remove(productMetaService.getProductMetaById(productMetaId));
        productService.saveProduct(product);
        redirectAttributes.addAttribute("productId", product.getId());
        productMetaService.deleteProductMeta(productMetaId);
        return "redirect:/product-management/product-meta-management/{productId}";
    }

    @GetMapping("update-product-meta-form/{id}")
    public String updateProductMeta(@PathVariable("id") Long productMetaId, Model model){
        model.addAttribute("productMeta", productMetaService.getProductMetaById(productMetaId));
        return "update-product-meta-form";
    }


    @GetMapping("add-new-product-meta/productId={productId}")
    public String getAddNewProductMetaView(@PathVariable("productId") Integer productId, Model model){
        ProductMeta productMeta = new ProductMeta();
        productMeta.setProduct(productService.getProductById(productId));
        model.addAttribute("productMeta", productMeta);
        return "update-product-meta-form";
    }

    @PostMapping("save-product-meta")
    public String saveNewProductMeta(@ModelAttribute("productMeta") ProductMeta productMeta,
                                     RedirectAttributes redirectAttributes){
        if (productMeta.getId() == null){
            Product product = productMeta.getProduct();
            product.getProductMetas().add(productMeta);
            productService.saveProduct(product);
            redirectAttributes.addAttribute("productId", product.getId());

        } else {
            Product product = productService.getProductByProductMeta(productMeta.getId());
            productMeta.setProduct(product);
            redirectAttributes.addAttribute("productId", product.getId());
        }
        productMetaService.saveProductMeta(productMeta);
        return "redirect:/product-management/product-meta-management/{productId}";
    }

}
