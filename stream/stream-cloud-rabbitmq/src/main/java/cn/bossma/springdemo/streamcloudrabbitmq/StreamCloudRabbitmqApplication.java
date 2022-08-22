package cn.bossma.springdemo.streamcloudrabbitmq;

import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class StreamCloudRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamCloudRabbitmqApplication.class, args);
    }

    @Bean
    Consumer<Message> justEvent() {

        return msg -> {
            Long inputValue = Longs.fromByteArray(msg.getBody());
            log.info("I received {}", inputValue);
        };
    }

    @Bean
    Consumer<Message> justDo() {
        return msg -> {
            Long inputValue = Longs.fromByteArray(msg.getBody());
            log.info("I do {}", inputValue);
        };
    }
}
