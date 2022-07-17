package cn.bossma.springdemno.nosql.cache;

import cn.bossma.springdemno.nosql.cache.model.Product;
import cn.bossma.springdemno.nosql.cache.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories
@EnableCaching(proxyTargetClass = true)
public class NosqlCacheApplication implements ApplicationRunner {

    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(NosqlCacheApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Product product = Product.builder()
                .name("Plane")
                .description("Just a toy!")
                .build();
        product = productService.insertProduct(product);

        for (int i = 0; i < 5; i++) {
            var t = productService.getProductById(product.getId());
            log.info("getProductById:{}", t);
        }
        Thread.sleep(10000);
        for (int i = 0; i < 5; i++) {
            var t = productService.getProductById(product.getId());
            log.info("getProductById:{}", t);
        }
    }
}
