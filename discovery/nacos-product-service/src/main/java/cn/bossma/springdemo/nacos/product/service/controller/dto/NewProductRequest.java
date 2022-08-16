package cn.bossma.springdemo.nacos.product.service.controller.dto;

import lombok.*;
import org.joda.money.Money;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class NewProductRequest {
    @NotEmpty
    @NotNull
    private String name;
    @NotNull
    private Money price;
    private String description;
}
