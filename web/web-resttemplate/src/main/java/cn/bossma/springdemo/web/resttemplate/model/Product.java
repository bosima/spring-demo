package cn.bossma.springdemo.web.resttemplate.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private Date createTime;
}
