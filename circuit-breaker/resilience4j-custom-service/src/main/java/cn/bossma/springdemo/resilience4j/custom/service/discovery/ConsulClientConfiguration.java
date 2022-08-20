package cn.bossma.springdemo.resilience4j.custom.service.discovery;

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

