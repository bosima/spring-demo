package cn.bossma.springdemo.eureka.server.repository;

import cn.bossma.springdemo.eureka.server.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select t from t_product t where t.name = ?1")
    @NonNull
    Optional<Product> findByName(String name);
}