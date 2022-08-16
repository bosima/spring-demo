package cn.bossma.springdemo.custom.custom.service;

import cn.bossma.springdemo.custom.custom.service.integration.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
@EnableAspectJAutoProxy
public class FeignCustomServiceApplication implements ApplicationRunner {
    @Autowired
    ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(FeignCustomServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 20; i++) {
            try {
                var product = productService.getById(1L);
                log.info("getById:{}", product);
            } catch (Exception ex) {
                log.error("Exception:" + ex.getMessage());
            }
        }
    }
}
