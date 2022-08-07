package cn.bossma.springdemo.actuator.health.indicator.service;

import cn.bossma.springdemo.actuator.health.indicator.model.Product;
import cn.bossma.springdemo.actuator.health.indicator.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Mono<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Flux<Product> findAllByName(String name) {
        return productRepository.findAllByName(name);
    }

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }
}
