package cn.bossma.springdemo.nosql.redis.repository;

import cn.bossma.springdemo.nosql.redis.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Long> {
    Optional<Product> findOneByName(String name);
}
