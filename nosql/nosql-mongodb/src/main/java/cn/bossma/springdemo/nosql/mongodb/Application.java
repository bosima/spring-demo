package cn.bossma.springdemo.nosql.mongodb;

import cn.bossma.springdemo.nosql.mongodb.model.Product;
import cn.bossma.springdemo.nosql.mongodb.repository.ProductRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.XSlf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Date;

@SpringBootApplication
@XSlf4j
@EnableMongoRepositories
public class Application implements ApplicationRunner {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ProductRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        runTemplate();
        runRepository();
    }

    private void runRepository() throws InterruptedException {
        Product product = Product.builder()
                .name("ZhangSan")
                .desc("I am a human.")
                .price(Money.of(CurrencyUnit.of("CNY"), 1000000.00))
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        repository.insert(product);

        log.info("insert:{}", product.getId());

        var product1 = repository.findById(product.getId());
        log.info("findById:{}", product1);

        Thread.sleep(1000);

        product.setName("LiSi");
        product.setUpdateTime(new Date());
        product = repository.save(product);
        log.info("UpdateResult:{}", product);

        var product2 = repository.findById(product.getId());
        log.info("findById:{}", product2);

        repository.deleteById(product.getId());
    }

    private void runTemplate() throws InterruptedException {
        Product product = Product.builder()
                .name("ZhangSan")
                .desc("I am a human.")
                .price(Money.of(CurrencyUnit.of("CNY"), 1000000.00))
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        product = mongoTemplate.insert(product);
        log.info("insert:{}", product.getId());

        product = mongoTemplate.findById(product.getId(), Product.class);
        log.info("findById:{}", product);

        Thread.sleep(1000);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(product.getId()));
        Update update = Update.update("name", "LiSi")
                .set("updateTime", new Date())
                .set("desc", "I am a good man!");
        UpdateResult result = mongoTemplate.updateFirst(query, update, Product.class);
        log.info("UpdateResult:{}", result);

        product = mongoTemplate.findById(product.getId(), Product.class);
        log.info("findById:{}", product);

        product.setName("WangWu");
        product=mongoTemplate.save(product);
        log.info("save:{}", product);

        mongoTemplate.remove(product);
    }
}
