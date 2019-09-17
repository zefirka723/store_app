package com.store.store.service;

import com.store.store.entity.Product;
import com.store.store.error_handler.ResourceNotFoundException;
import com.store.store.repository.ProductRepository;
import com.store.store.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public String getFiltresString(String word, Integer min, Integer max) {
        StringBuilder filtersBuilder = new StringBuilder();
        if (word != null && !word.isEmpty()) {
            filtersBuilder.append("&word=" + word);
        }
        if (min != null) {
            filtersBuilder.append("&minCost=" + min);
        }
        if (max != null) {
            filtersBuilder.append("&maxCost=" + max);
        }
        return filtersBuilder.toString();
    }


    public Specification<Product> getSpec(String word, Integer min, Integer max) {
        Specification<Product> spec = Specification.where(null);
        if (word != null && !word.isEmpty()) {
            spec = spec.and(ProductSpecification.labelContains(word));
        }
        if (min != null) {
            spec = spec.and(ProductSpecification.costGreaterThanOrEq(min));
        }
        if (max != null) {
            spec = spec.and(ProductSpecification.costLesserThanOrEq(max));
        }
        return spec;
    }

    public Page<Product> findAllByPagingAndFiltering(Specification<Product> specification, Pageable pageable) {
        return productRepository.findAll(specification, pageable);
    }




    public void save(Product product) {
        productRepository.save(product);
    }


    // для реста
    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long productId) {
        productRepository.delete(productRepository
                .findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product with id = " + productId + " not found")));
    }

    public List<Product> getAllProducts() {
        return (List)productRepository.findAll();
    }

    public Product getOneById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product with id = " + id + " not found"));
    }
}
