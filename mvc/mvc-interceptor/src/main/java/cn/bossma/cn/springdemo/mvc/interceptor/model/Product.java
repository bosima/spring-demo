package cn.bossma.cn.springdemo.mvc.interceptor.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private String name;
    private Date createTime;
}
