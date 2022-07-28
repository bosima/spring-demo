package cn.bossma.springdemo.web.hateoas.repository;

import cn.bossma.springdemo.web.hateoas.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "products")
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameInOrderById(List<String> list);
    Product findByName(String name);
}