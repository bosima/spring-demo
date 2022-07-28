package cn.bossma.springdemo.web.hateoas.convert;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MoneyToLongConverter implements AttributeConverter<Money, Long> {

    @Override
    public Long convertToDatabaseColumn(Money money) {
        return money.getAmountMinorLong();
    }

    @Override
    public Money convertToEntityAttribute(Long longValue) {
        return Money.ofMinor(CurrencyUnit.of("CNY"), longValue);
    }
}
