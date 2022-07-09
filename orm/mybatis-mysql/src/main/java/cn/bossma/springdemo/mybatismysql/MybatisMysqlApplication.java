package cn.bossma.springdemo.mybatismysql;

import cn.bossma.springdemo.mybatismysql.mapper.ProductMapper;
import cn.bossma.springdemo.mybatismysql.model.Product;
import cn.bossma.springdemo.mybatismysql.model.ProductExample;
import lombok.extern.slf4j.XSlf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
@XSlf4j
@MapperScan("cn.bossma.springdemo.mybatismysql.mapper")
public class MybatisMysqlApplication implements ApplicationRunner {

    @Autowired
    private ProductMapper productMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisMysqlApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var product0 = new Product()
                .withName("Food")
                .withPrice(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .withDescription("I am a food")
                .withCreateTime(new Date())
                .withUpdateTime(new Date());
        productMapper.insert(product0);
        log.info("Add Product Id: {}", product0.getId());

        var product = new Product()
                .withName("Book")
                .withPrice(Money.of(CurrencyUnit.of("CNY"), 100.0))
                .withDescription("I am a book")
                .withCreateTime(new Date())
                .withUpdateTime(new Date());
        productMapper.insert(product);
        log.info("Add Product Id: {}", product.getId());

        var example = new ProductExample();
        example.createCriteria().andNameEqualTo("Book");
        var list = productMapper.selectByExample(example);
        log.info("selectByExample: {}", list);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 0, 1, 0, 0, 0);
        list = productMapper.selectByCreateTime(calendar.getTime());
        log.info("selectByCreateTime: {}", list);

        list = productMapper.selectByName("Book");
        log.info("selectByName: {}", list);

        var deleteResult0 = productMapper.deleteByPrimaryKey(product0.getId());
        log.info("deleteByPrimaryKey 0: {}", deleteResult0);

        var deleteResult = productMapper.deleteByPrimaryKey(product.getId());
        log.info("deleteByPrimaryKey: {}", deleteResult);
    }
}
