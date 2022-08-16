package cn.bossma.springdemo.custom.product.service.registry;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(ConsulClientConfiguration.class)
public class CustomServiceRegistryConfiguration {
    @Bean
    public CustomServiceRegistry consulServiceRegistry() {
        return new CustomServiceRegistry();
    }
}
