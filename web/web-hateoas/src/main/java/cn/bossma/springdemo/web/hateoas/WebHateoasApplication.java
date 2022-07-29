package cn.bossma.springdemo.web.hateoas;

import cn.bossma.springdemo.web.hateoas.model.Product;
import cn.bossma.springdemo.web.hateoas.repository.ProductRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WebHateoasApplication {
	@Autowired
	private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebHateoasApplication.class, args);
    }

    @Bean
    public ApplicationRunner initApplication() {
        return args -> {
			productRepository.save(Product.builder()
					.name("Food")
					.price(Money.of(CurrencyUnit.of("CNY"),19.99))
					.build());

			productRepository.save(Product.builder()
					.name("Book")
					.price(Money.of(CurrencyUnit.of("CNY"),59.99))
					.build());
        };
    }
}
