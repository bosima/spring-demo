package cn.bossma.springdemno.nosql.cache.service;

import cn.bossma.springdemno.nosql.cache.model.Product;
import cn.bossma.springdemno.nosql.cache.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = "product")
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @CacheEvict
    public void reloadProduct() {
    }

    @Cacheable
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product insertProduct(Product product) {
        return productRepository.save(product);
    }
}
