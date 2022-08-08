package cn.bossma.springdemo.server.tomcat.custom;

import cn.bossma.springdemo.server.tomcat.custom.model.Product;
import cn.bossma.springdemo.server.tomcat.custom.service.ProductService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Compression;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableScheduling
public class TomcatCustomApplication
//        implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>
{

    public static void main(String[] args) {
        SpringApplication.run(TomcatCustomApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ProductService service) {
        return args -> {
            service.insert(Product.builder()
                    .name("Book")
                    .description("This is a book.")
                    .price(Money.of(CurrencyUnit.of("CNY"), 99.99))
                    .build());

            service.insert(Product.builder()
                    .name("Box")
                    .description("This is a box.")
                    .price(Money.of(CurrencyUnit.of("CNY"), 10.00))
                    .build());
        };
    }

//    @Override
//    public void customize(TomcatServletWebServerFactory factory) {
//        Compression compression = new Compression();
//        compression.setEnabled(true);
//        compression.setMinResponseSize(DataSize.of(512, DataUnit.BYTES));
//        factory.setCompression(compression);
//    }
}
