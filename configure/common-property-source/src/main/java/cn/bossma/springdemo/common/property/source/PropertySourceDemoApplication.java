package cn.bossma.springdemo.common.property.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
@EnableAutoConfiguration
public class PropertySourceDemoApplication implements ApplicationRunner {
    @Value("${greeting.name}")
    private String greetingName;

    public static void main(String[] args) {
        SpringApplication
                .run(PropertySourceDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Hello {}", greetingName);
    }
}
