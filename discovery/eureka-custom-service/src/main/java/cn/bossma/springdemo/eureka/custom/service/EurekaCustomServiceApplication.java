package cn.bossma.springdemo.eureka.custom.service;

import cn.bossma.springdemo.eureka.custom.service.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class EurekaCustomServiceApplication implements ApplicationRunner {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DiscoveryClient discoveryClient;

    public static void main(String[] args) {
        SpringApplication.run(EurekaCustomServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("discoveryClient:{}",discoveryClient.getClass().getName());
        discoveryClient.getInstances("product.service").forEach(s->{
            log.info("{}:{}",s.getHost(),s.getPort());
        });

		var entity = restTemplate.getForEntity("http://product.service/product/1", Product.class);
		log.info("getForEntity:{}", entity);
		entity.getHeaders().forEach((k, v) -> {
			log.info("{}:{}", k, v);
		});
		log.info("getForEntity->Object:{}", entity.getBody());
    }
}
