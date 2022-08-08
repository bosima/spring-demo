package cn.bossma.springdemo.actuator.metric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableR2dbcRepositories
public class ActuatorMetricApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActuatorMetricApplication.class, args);
	}

}
