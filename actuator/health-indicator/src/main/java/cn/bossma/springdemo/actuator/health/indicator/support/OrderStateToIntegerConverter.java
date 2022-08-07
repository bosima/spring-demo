package cn.bossma.springdemo.actuator.health.indicator.support;

import cn.bossma.springdemo.actuator.health.indicator.model.OrderState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class OrderStateToIntegerConverter implements Converter<OrderState, Integer> {
    @Override
    public Integer convert(OrderState source) {
        return source.ordinal();
    }
}
