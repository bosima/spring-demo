package cn.bossma.springdemo.mybatis;

import cn.bossma.springdemo.mybatis.mapper.ProductMapper;
import cn.bossma.springdemo.mybatis.model.Product;
import cn.bossma.springdemo.mybatis.model.ProductExample;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("cn.bossma.springdemo.mybatis.mapper")
public class MybatisGeneratorApplication implements ApplicationRunner {
    @Autowired
    private ProductMapper productMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisGeneratorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // H2 in memory here, so generate on runtime.
        //generateArtifacts();

        Product product = new Product()
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
    }

    private void generateArtifacts() throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(
                this.getClass().getResourceAsStream("/generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
