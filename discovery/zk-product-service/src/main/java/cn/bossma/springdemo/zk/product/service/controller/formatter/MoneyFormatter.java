package cn.bossma.springdemo.zk.product.service.controller.formatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class MoneyFormatter implements Formatter<Money> {
    @Override
    public Money parse(String text, Locale locale) throws ParseException {
        if (NumberUtils.isParsable(text)) {
            return Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(text));
        } else if (StringUtils.isNotEmpty(text)) {
            var textSplit = StringUtils.split(text, " ");
            if (textSplit != null && textSplit.length > 1 && NumberUtils.isParsable(textSplit[1])) {
                return Money.of(CurrencyUnit.of(textSplit[0]), NumberUtils.createBigDecimal(textSplit[1]));
            } else {
                throw new ParseException(text, 0);
            }
        }
        throw new ParseException(text, 0);
    }

    @Override
    public String print(Money object, Locale locale) {
        return object == null ? null : object.getAmount().toString();
    }
}
