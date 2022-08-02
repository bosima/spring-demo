package cn.bossma.springdemo.mv.webflux.support;

import cn.bossma.springdemo.mv.webflux.model.OrderState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class IntegerToOrderStateConverter implements Converter<Integer, OrderState> {
    @Override
    public OrderState convert(Integer source) {
        return OrderState.values()[source];
    }
}
