package cn.bossma.springdemo.ormjpa.repository;

import cn.bossma.springdemo.ormjpa.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product,Long> {
    List<Product> findByName(String name);
    List<Product> findTop2ByOrderByUpdateTimeDescIdAsc();
}
