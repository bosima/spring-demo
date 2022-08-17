package cn.bossma.springdemo.hystrix.custom.service;

import cn.bossma.springdemo.hystrix.custom.service.integration.ProductService;
import cn.bossma.springdemo.hystrix.custom.service.integration.ProductServiceWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableCircuitBreaker
public class FeignCustomServiceApplication implements ApplicationRunner {
    @Autowired
    ProductService productService;

    @Autowired
    ProductServiceWrapper productServiceWrapper;

    public static void main(String[] args) {
        SpringApplication.run(FeignCustomServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 3; i++) {
            try {
                var product = productServiceWrapper.getById(1L);
                log.info("getById:{}", product);
            } catch (Exception ex) {
                log.error("Exception:" + ex.getMessage());
            }
        }

        for (int i = 0; i < 3; i++) {
            try {
                var product = productService.getByName("Book");
                log.info("getByName:{}", product);
            } catch (Exception ex) {
                log.error("Exception:" + ex.getMessage());
            }
        }

        for (int i = 0; i < 3; i++) {
            try {
                var products = productService.getAll();
                log.info("getAll:{}", products);
            } catch (Exception ex) {
                log.error("Exception:" + ex.getMessage());
            }
        }
    }
}
