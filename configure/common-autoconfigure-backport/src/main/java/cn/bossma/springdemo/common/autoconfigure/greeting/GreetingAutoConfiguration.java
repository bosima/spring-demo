package cn.bossma.springdemo.common.autoconfigure.greeting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingAutoConfiguration {
    @Bean
    public static GreetingBeanFactoryPostProcessor greetingBeanFactoryPostProcessor() {
        return new GreetingBeanFactoryPostProcessor();
    }
}
