package cn.bossma.springdemo.mv.webflux.repository;

import cn.bossma.springdemo.mv.webflux.model.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    @Query("select * from t_product where name=:name")
    Flux<Product> findAllByName(String name);
}
