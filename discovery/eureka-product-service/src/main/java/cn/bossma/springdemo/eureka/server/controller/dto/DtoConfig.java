package cn.bossma.springdemo.eureka.server.controller.dto;

import cn.bossma.springdemo.eureka.server.model.Product;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DtoConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        Converter<Money, BigDecimal> toBigDecimal =
                ctx -> ctx.getSource() == null ? null : ctx.getSource().getAmount();

        Converter<BigDecimal,Money> toMoney =
                ctx -> Money.of(CurrencyUnit.of("CNY"), ctx.getSource());

        modelMapper.typeMap(Product.class, ProductDto.class).addMappings(mapper -> {
            mapper.using(toBigDecimal).map(Product::getPrice, ProductDto::setPrice);
        });

        modelMapper.typeMap(ProductDto.class, Product.class).addMappings(mapper -> {
            mapper.using(toMoney).map(ProductDto::getPrice, Product::setPrice);
        });

        return modelMapper;
    }
}
