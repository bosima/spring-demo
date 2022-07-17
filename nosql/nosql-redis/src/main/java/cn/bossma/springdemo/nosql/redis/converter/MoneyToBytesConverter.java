package cn.bossma.springdemo.nosql.redis.converter;

import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.nio.charset.StandardCharsets;

@WritingConverter
public class MoneyToBytesConverter implements Converter<Money, byte[]> {
    @Override
    public byte[] convert(Money source) {
        var value = source.getAmountMinorLong();
        return Long.toString(value).getBytes(StandardCharsets.UTF_8);
    }
}
