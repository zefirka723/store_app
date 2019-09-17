package com.store.store.controller;

import com.store.store.entity.Product;
import com.store.store.error_handler.ResourceNotFoundException;
import com.store.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getOneById(productId);
    }

    @GetMapping("/")
    public List<Product> getAllStudents() {
        return productService.getAllProducts();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addStudent(@RequestBody Product theProduct) {
        theProduct.setId(null);
        theProduct = productService.saveOrUpdate(theProduct);
        return theProduct;
    }

    @PutMapping(path = "/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Product updateStudent(@RequestBody Product theProduct) {
        if (productService.getOneById(theProduct.getId()) != null) {
            return productService.saveOrUpdate(theProduct);
        } else throw new ResourceNotFoundException("Product with id = " + theProduct.getId() + "not found");
    }

    @DeleteMapping("/{productId}")
    public int deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
        return HttpStatus.OK.value();
    }

}
