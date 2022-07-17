package cn.bossma.springdemo.orm.mybatis.mysql;

import cn.bossma.springdemo.orm.mybatis.mysql.mapper.ProductMapper;
import cn.bossma.springdemo.orm.mybatis.mysql.model.Product;
import cn.bossma.springdemo.orm.mybatis.mysql.model.ProductExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
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
@MapperScan("cn.bossma.springdemo.orm.mybatis.mysql.mapper")
public class Application implements ApplicationRunner {

    @Autowired
    private ProductMapper productMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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

        var product1 = new Product()
                .withName("Book")
                .withPrice(Money.of(CurrencyUnit.of("CNY"), 100.0))
                .withDescription("I am a book")
                .withCreateTime(new Date())
                .withUpdateTime(new Date());
        productMapper.insert(product1);
        log.info("Add Product Id: {}", product1.getId());

        var product2 = new Product()
                .withName("Dog")
                .withPrice(Money.of(CurrencyUnit.of("CNY"), 100.0))
                .withDescription("I am a dog")
                .withCreateTime(new Date())
                .withUpdateTime(new Date());
        productMapper.insert(product2);
        log.info("Add Product Id: {}", product2.getId());

        var example = new ProductExample();
        example.createCriteria().andNameEqualTo("Book");
        var list = productMapper.selectByExample(example);
        log.info("selectByExample: {}", list);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 0, 1, 0, 0, 0);
        PageHelper.startPage(1, 2);
        list = productMapper.selectByCreateTime(calendar.getTime());
        log.info("selectByCreateTime: {}", list);

        list = productMapper.selectByName("Book");
        log.info("selectByName: {}", list);

        list = productMapper.selectList(new PageRowBounds(1, 2));
        log.info("selectList 1: {}", list);

        list = productMapper.selectList(new PageRowBounds(2, 2));
        log.info("selectList 2: {}", list);

        PageInfo page = new PageInfo(list);
        log.info("selectList PageInfo: {}", page);

        list = productMapper.selectByPageNumSize(1, 2);
        log.info("selectByPageNumSize 1: {}", list);

        list = productMapper.selectByPageNumSize(2, 2);
        log.info("selectByPageNumSize 2: {}", list);

        page = new PageInfo(list);
        log.info("selectByPageNumSize PageInfo: {}", page);

        var deleteResult0 = productMapper.deleteByPrimaryKey(product0.getId());
        log.info("deleteByPrimaryKey: {}", deleteResult0);

        var deleteResult1 = productMapper.deleteByPrimaryKey(product1.getId());
        log.info("deleteByPrimaryKey: {}", deleteResult1);

        var deleteResult2 = productMapper.deleteByPrimaryKey(product2.getId());
        log.info("deleteByPrimaryKey: {}", deleteResult2);
    }
}
