package cn.bossma.springdemo.common.aop;

import cn.bossma.springdemo.common.aop.model.Product;
import cn.bossma.springdemo.common.aop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaRepositories
@Slf4j
@EnableCaching(proxyTargetClass = true)
public class CommonAopApplication implements ApplicationRunner {

    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(CommonAopApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Product product = productService.insert(Product.builder()
                .name("ZhangSan")
                .description("A good man!")
                .addTime(new Date())
                .updateTime(new Date())
                .build());

        for (int i = 0; i < 5; i++) {
            Product productOne = productService.getById(product.getId());
            log.info("getById:{}", productOne);
        }

        Thread.sleep(10000);

        for (int i = 0; i < 5; i++) {
            Product productOne = productService.getById(product.getId());
            log.info("getById:{}", productOne);
        }
    }
}
