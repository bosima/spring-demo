package cn.bossma.springdemo.mvc.restful.repository.convert;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MoneyAttributeConverter implements AttributeConverter<Money, Long> {

    @Override
    public Long convertToDatabaseColumn(Money money) {
        return money.getAmountMinorLong();
    }

    @Override
    public Money convertToEntityAttribute(Long aLong) {
        return Money.ofMinor(CurrencyUnit.of("CNY"), aLong);
    }
}
