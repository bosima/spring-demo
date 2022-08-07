package cn.bossma.springdemo.actuator.health.indicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableR2dbcRepositories
public class HealthIndicatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthIndicatorApplication.class, args);
	}

}
