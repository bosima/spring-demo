package cn.bossma.springdemo.nosql.redis;

import cn.bossma.springdemo.nosql.redis.converter.BytesToMoneyConverter;
import cn.bossma.springdemo.nosql.redis.converter.MoneyToBytesConverter;
import cn.bossma.springdemo.nosql.redis.model.Product;
import cn.bossma.springdemo.nosql.redis.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
@Slf4j
public class Application implements ApplicationRunner {
    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RedisTemplate<String, String> template;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

    @Autowired
    private ProductRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("----Jedis Client-----");
        log.info(jedisPoolConfig.toString());
        try (var jedis = jedisPool.getResource()) {
            var setResult = jedis.set("Hello", "Bosima");
            log.info("setResult:{}", setResult);

            var getResult = jedis.get("Hello");
            log.info("getResult:{}", getResult);
        }

        log.info("----Redis Template-----");
        log.info("current Connection:{}", template.getConnectionFactory().getConnection());
        valueOps.set("Hello", "Bossma");
        String value = valueOps.get("Hello");
        log.info("valueOps.get:{}", value);

        log.info("----Redis Repository-----");
        Product product = Product.builder()
                .name("Food")
                .price(Money.of(CurrencyUnit.of("CNY"),20.00))
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        product = repository.save(product);
        log.info("repository.save:{}", product);

        var product1 = repository.findById(product.getId());
        log.info("repository.findById:{}", product1);

        var product2 = repository.findOneByName(product.getName());
        log.info("repository.findOneByName:{}", product2);

    }
}
