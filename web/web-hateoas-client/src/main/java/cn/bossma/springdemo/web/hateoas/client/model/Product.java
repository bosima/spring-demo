package cn.bossma.springdemo.web.hateoas.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
