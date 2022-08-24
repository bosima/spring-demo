package cn.bossma.springdemo.streamcloudrabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class StreamCloudRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamCloudRabbitmqApplication.class, args);
    }

    @Bean
    Consumer<Message<Long>> justEvent() {

        return msg -> {
            Long inputValue = msg.getPayload();
            log.info("I received {}", inputValue);
        };
    }
}
