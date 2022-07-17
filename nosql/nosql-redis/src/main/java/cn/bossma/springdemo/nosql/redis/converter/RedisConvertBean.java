package cn.bossma.springdemo.nosql.redis.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RedisConvertBean {
    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(
                Arrays.asList(new MoneyToBytesConverter(), new BytesToMoneyConverter()));
    }
}
