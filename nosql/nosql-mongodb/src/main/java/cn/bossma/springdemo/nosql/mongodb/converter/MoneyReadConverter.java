package cn.bossma.springdemo.nosql.mongodb.converter;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MoneyReadConverter implements Converter<Document, Money> {
    @Override
    public Money convert(Document source) {
        Document money = (Document) source.get("money");
        Double amount = Double.parseDouble(money.getString("amount"));
        String currency = ((Document)money.get("currency")).getString("code");
        return Money.of(CurrencyUnit.of(currency), amount);
    }

    @Bean
    MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
    }
}
