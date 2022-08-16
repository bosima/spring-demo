package cn.bossma.springdemo.consul.product.service.service;

import cn.bossma.springdemo.consul.product.service.model.Product;
import cn.bossma.springdemo.consul.product.service.repository.ProductRepository;
import cn.bossma.springdemo.consul.product.service.error.ArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "product")
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product insert(Product product) {
        var exists = productRepository.exists(new Example<>() {
            @Override
            public Product getProbe() {
                return product;
            }

            @Override
            public ExampleMatcher getMatcher() {
                return ExampleMatcher.matching()
                        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                        .withIgnorePaths("price","description");
            }
        });

        if (exists) {
            throw new ArgumentException(product.getName() + " has exists!", 1001);
        }

        return productRepository.save(product);
    }

    @Cacheable(sync = true)
    public Product getByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    @Cacheable
    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Cacheable(sync = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @CacheEvict(allEntries = true, cacheNames = "product")
    @Scheduled(fixedDelay = 10 * 1000, initialDelay = 500)
    public void reportCacheEvict() {
        var dateFormat = new SimpleDateFormat();
        System.out.println("Flush Cache " + dateFormat.format(new Date()));
    }
}
