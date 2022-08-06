package cn.bossma.springdemo.common.autoconfigure;

import cn.bossma.springdemo.common.autoconfigure.greeting.GreetingApplicationRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class AutoConfigurationDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoConfigurationDemoApplication.class, args);
    }

// 优先于引入的自动配置
//    @Bean
//    public GreetingApplicationRunner greetingApplicationRunner(){
//        return new GreetingApplicationRunner("World");
//    }
}
