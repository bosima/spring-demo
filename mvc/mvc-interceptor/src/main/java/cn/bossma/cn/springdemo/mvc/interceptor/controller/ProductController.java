package cn.bossma.cn.springdemo.mvc.interceptor.controller;

import cn.bossma.cn.springdemo.mvc.interceptor.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/get/{id}")
    public Product getById(@PathVariable Long id) throws InterruptedException {
        Thread.sleep(100);
        return new Product(1L, "Book", new Date());
    }

}
