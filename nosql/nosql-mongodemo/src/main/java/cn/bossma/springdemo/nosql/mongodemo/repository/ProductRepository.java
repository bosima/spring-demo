package cn.bossma.springdemo.nosql.mongodemo.repository;

import cn.bossma.springdemo.nosql.mongodemo.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByName(String name);
}
