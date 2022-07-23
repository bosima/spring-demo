package cn.bossma.springdemo.mvc.restful.service;

import cn.bossma.springdemo.mvc.restful.model.Product;
import cn.bossma.springdemo.mvc.restful.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
