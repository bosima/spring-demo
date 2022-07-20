package cn.bossma.springdemo.r2dbc.h2;

import cn.bossma.springdemo.r2dbc.h2.model.Customer;
import cn.bossma.springdemo.r2dbc.h2.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class R2dbcH2Application {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcH2Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return args -> {
            repository.saveAll(Arrays.asList(
                    Customer.builder().firstName("Zhang").lastName("San").build(),
                    Customer.builder().firstName("Li").lastName("Si").build(),
                    Customer.builder().firstName("Wang").lastName("Wu").build()
            )).blockLast();
            log.info("---- saveAll over ----");

            repository.findAll()
                    .doOnNext(c -> log.info("Get Customer: {}", c))
                    .blockLast();

            repository.findById(1L).doOnNext(c -> {
                        log.info("findById:{}", c);
                    }
            ).block();

            repository.findAllByFistName("Wang").doOnNext(c -> {
                        log.info("findAllByFistName:{}", c);
                    }
            ).blockLast();
        };
    }
}
