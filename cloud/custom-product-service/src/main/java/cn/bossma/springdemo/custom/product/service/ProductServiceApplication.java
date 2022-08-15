package cn.bossma.springdemo.custom.product.service;

import cn.bossma.springdemo.custom.product.service.model.Product;
import cn.bossma.springdemo.custom.product.service.service.ProductService;
import com.ecwid.consul.v1.ConsulClient;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
public class ProductServiceApplication {
    @Autowired
    ConsulClient consulClient;

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ProductService service) {

        return args -> {
            service.insert(Product.builder()
                    .name("Book")
                    .description("This is a book.")
                    .price(Money.of(CurrencyUnit.of("CNY"), 99.99))
                    .build());

            service.insert(Product.builder()
                    .name("Box")
                    .description("This is a box.")
                    .price(Money.of(CurrencyUnit.of("CNY"), 10.00))
                    .build());
        };
    }
}
