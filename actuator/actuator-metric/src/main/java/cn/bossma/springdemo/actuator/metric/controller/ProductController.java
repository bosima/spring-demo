package cn.bossma.springdemo.actuator.metric.controller;

import cn.bossma.springdemo.actuator.metric.controller.request.OrderRequest;
import cn.bossma.springdemo.actuator.metric.model.Product;
import cn.bossma.springdemo.actuator.metric.model.ProductOrder;
import cn.bossma.springdemo.actuator.metric.service.OrderService;
import cn.bossma.springdemo.actuator.metric.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/")
    public Flux<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<Product> getById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping(path = "/order/{id}")
    public Mono<ProductOrder> getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping("/order")
    public Mono<ProductOrder> order(@RequestBody OrderRequest request) {
        return orderService.add(request.getCustomer(), request.getProductIds());
    }
}
