package cn.bossma.springdemo.nosql.redis.reactive;

import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.UUID;

@Component
public class ProductLoader {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveStringRedisTemplate template;

    public ProductLoader(ReactiveRedisConnectionFactory factory, ReactiveStringRedisTemplate template) {
        this.factory = factory;
        this.template = template;
    }

    @PostConstruct
    public void loadData() {
        factory.getReactiveConnection().serverCommands().flushAll()
                .thenMany(
                        Flux.just("Food", "Toy", "Cloth")
                                .map(p -> new Product(UUID.randomUUID().toString(), p))
                                .flatMap(product -> template.opsForValue().set(product.getId(), JSON.toJSONString(product)))
                )
                .thenMany(template.keys("*")
                        .flatMap(key -> {
                            return template.opsForValue().getAndExpire(key, Duration.ofMillis(60));
                        })
                )
                .subscribe(System.out::println);
    }
}
