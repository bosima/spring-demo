package cn.bossma.springdemo.mv.webflux.support;

import cn.bossma.springdemo.mv.webflux.model.OrderState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class OrderStateToIntegerConverter implements Converter<OrderState, Integer> {
    @Override
    public Integer convert(OrderState source) {
        return source.ordinal();
    }
}
