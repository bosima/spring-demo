package cn.bossma.springdemo.nosql.redis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "product",timeToLive = 60)
public class Product {
    @Id
    private Long id;

    @Indexed
    private String name;

    private Money price;

    private Date createTime;

    private Date updateTime;
}
