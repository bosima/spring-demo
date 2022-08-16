package cn.bossma.springdemo.custom.product.service.registry;

import com.ecwid.consul.v1.ConsulClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsulClientConfiguration {
    @Bean
    public ConsulClient consulClient() {
        return new ConsulClient("localhost");
    }
}
