package com.store.store.service;

import com.store.store.entity.Product;
import com.store.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Value("${spring.data.rest.max-page-size}")
    private int pageSize;

    public Page<Product> getProductsByFiltres(Integer minCost, Integer maxCost, Integer pageNumber) {
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (minCost == null) {
            minCost = 0;
        }
        if (maxCost == null) {
            maxCost = Integer.MAX_VALUE;
        }
        return productRepository.
                findAllByCostBetween(
                        minCost, maxCost,
                        PageRequest.of(pageNumber, pageSize, new Sort(Sort.Direction.ASC, "id")));
    }


    public Long getTotalPagesCount() {
        return productRepository.count() / pageSize;
    }

    public Product getOneById(Long id) {
        return productRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public void save(Product product) {
        productRepository.save(product);
    }
}
