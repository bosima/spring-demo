package cn.bossma.springdemo.mybatis;

import cn.bossma.springdemo.mybatis.mapper.ProductMapper;
import cn.bossma.springdemo.mybatis.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("cn.bossma.springdemo.mybatis.mapper")
public class OrmMybatisApplication implements ApplicationRunner {
    @Autowired
    private ProductMapper productMapper;

    public static void main(String[] args) {
        SpringApplication.run(OrmMybatisApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Product product = Product.builder()
                .name("book")
                .description("I am a book")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .build();
        productMapper.save(product);
        log.info("Save product id is {}", product.getId());

        product = productMapper.findById(product.getId());
        log.info("Query product is {}", product);
    }
}
