package cn.bossma.springdemo.common.aop.service;

import cn.bossma.springdemo.common.aop.model.Product;
import cn.bossma.springdemo.common.aop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "product")
public class ProductService {
    @NotNull
    private final ProductRepository productRepository;

    @CacheEvict
    public void reload() {
    }

    @Cacheable
    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product insert(Product product) {
        return productRepository.save(product);
    }
}
