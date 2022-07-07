package cn.bossma.springdemo.ormjpa;

import cn.bossma.springdemo.ormjpa.model.Product;
import cn.bossma.springdemo.ormjpa.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.stream.Collectors;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class OrmJpaApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(OrmJpaApplication.class, args);
    }

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var book = Product.builder()
                .name("Book")
                .description("I am a book.")
                .build();
        book = productRepository.save(book);
        log.info("Product1: {}", book);

        var food = Product.builder()
                .name("Food")
                .description("I am a food.")
                .build();
        food = productRepository.save(food);
        log.info("Product2: {}", food);

        var count = productRepository.count();
        log.info("Product Count: {}", count);

        var product = productRepository.findById(food.getId());
        log.info("findById: {}", product);

        var productList = productRepository.findTop2ByOrderByUpdateTimeDescIdAsc();
        var ids = productList.stream().map(p -> p.getId()).collect(Collectors.toList());
        log.info("findTop2ByOrderByUpdateTimeDescIdAsc: {}", ids);

        productList = productRepository.findByName("Book");
        product = productList.stream().findFirst();
        log.info("findByName: {}", product);

        productRepository.deleteById(food.getId());
        count = productRepository.count();
        log.info("Product Count: {}", count);

        product = productRepository.findById(food.getId());
        log.info("findById: {}", product);
    }
}
