package cn.bossma.springdemo.actuator.metric.support;

import cn.bossma.springdemo.actuator.metric.model.OrderState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class IntegerToOrderStateConverter implements Converter<Integer, OrderState> {
    @Override
    public OrderState convert(Integer source) {
        return OrderState.values()[source];
    }
}
