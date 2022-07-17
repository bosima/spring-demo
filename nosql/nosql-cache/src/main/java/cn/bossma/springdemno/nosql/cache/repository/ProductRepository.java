package cn.bossma.springdemno.nosql.cache.repository;

import cn.bossma.springdemno.nosql.cache.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product,Long> {
}
