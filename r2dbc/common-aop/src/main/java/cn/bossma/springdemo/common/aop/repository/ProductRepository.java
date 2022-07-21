package cn.bossma.springdemo.common.aop.repository;

import cn.bossma.springdemo.common.aop.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
