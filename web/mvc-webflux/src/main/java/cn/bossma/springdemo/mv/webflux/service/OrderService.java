package cn.bossma.springdemo.mv.webflux.service;

import cn.bossma.springdemo.mv.webflux.model.OrderState;
import cn.bossma.springdemo.mv.webflux.model.ProductOrder;
import cn.bossma.springdemo.mv.webflux.repository.OrderRepository;
import cn.bossma.springdemo.mv.webflux.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    public Mono<ProductOrder> findById(Long id) {
        return orderRepository.getById(id);
    }

    public Mono<ProductOrder> add(String customer, List<Long> productIds) {
        return Flux.fromIterable(productIds)
                .flatMap(pid -> productRepository.findById(pid))
                .collectList()
                .flatMap(l ->
                        orderRepository.save(ProductOrder.builder()
                                .customer(customer)
                                .state(OrderState.INIT)
                                .products(l)
                                .createTime(LocalDateTime.now())
                                .updateTime(LocalDateTime.now())
                                .build()))
                .flatMap(id -> findById(id));

    }
}
