package cn.bossma.springdemo.nosql.redis.reactive;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import reactor.core.publisher.Flux;

// https://spring.io/guides/gs/spring-data-reactive-redis/

@SpringBootApplication
@Slf4j
public class NosqlRedisReactiveApplication implements ApplicationRunner {
    @Autowired
    ReactiveStringRedisTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(NosqlRedisReactiveApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getAllProduct().subscribe(s->{
            Product p = JSON.parseObject(s,Product.class);
            log.info("Receive {}",p);
        });
    }

    private Flux<String> getAllProduct(){
        return template.keys("*")
                .flatMap(template.opsForValue()::get);
    }
}
