package cn.bossma.springdemo.mv.webflux.service;

import cn.bossma.springdemo.mv.webflux.model.Product;
import cn.bossma.springdemo.mv.webflux.repository.ProductRepository;
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
