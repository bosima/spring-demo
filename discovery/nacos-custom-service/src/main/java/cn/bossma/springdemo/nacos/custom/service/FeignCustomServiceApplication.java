package cn.bossma.springdemo.nacos.custom.service;

import cn.bossma.springdemo.nacos.custom.service.dto.ProductDto;
import cn.bossma.springdemo.nacos.custom.service.integration.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
public class FeignCustomServiceApplication implements ApplicationRunner {
    @Autowired
    ProductService productService;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(FeignCustomServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var product0 = restTemplate().getForObject("http://product-service/product/1", ProductDto.class);
        log.info("getForObject:{}", product0);

        var product = productService.getById(1L);
        log.info("getById:{}", product);

        var product2 = productService.getByName("Box");
        log.info("getByName:{}", product2);

        var productList = productService.getAll();
        log.info("getAll:{}", productList);
    }
}
