package cn.bossma.springdemo.mvc.restful;

import cn.bossma.springdemo.mvc.restful.model.Product;
import cn.bossma.springdemo.mvc.restful.service.ProductService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableScheduling
public class MvcRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcRestfulApplication.class, args);
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
