package cn.bossma.springdemo.mv.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableR2dbcRepositories
public class MvcWebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcWebFluxApplication.class, args);
	}

}
