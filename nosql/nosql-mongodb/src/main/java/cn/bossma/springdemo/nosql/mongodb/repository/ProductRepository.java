package cn.bossma.springdemo.nosql.mongodb.repository;

import cn.bossma.springdemo.nosql.mongodb.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByName(String name);
}
