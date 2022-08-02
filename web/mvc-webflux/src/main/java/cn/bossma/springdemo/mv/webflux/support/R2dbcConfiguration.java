package cn.bossma.springdemo.mv.webflux.support;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.H2Dialect;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;

import java.util.Arrays;

@Configuration
public class R2dbcConfiguration {
    @Bean
    R2dbcCustomConversions r2dbcCustomConversions(ConnectionFactory connectionFactory) {
        R2dbcDialect dialect = new H2Dialect();
        return R2dbcCustomConversions.of(dialect,
                Arrays.asList(new LongToMoneyConverter(),
                        new MoneyToLongConverter(),
                        new OrderStateToIntegerConverter(),
                        new IntegerToOrderStateConverter()));
    }
}
