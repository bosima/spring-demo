package cn.bossma.springdemo.mv.webflux.support;

import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class MoneyToLongConverter implements Converter<Money, Long> {
    @Override
    public Long convert(Money money) {
        return money.getAmountMinorLong();
    }
}
