package cn.bossma.springdemo.streamcloudrabbitmq.controller;

import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("demo")
@Slf4j
public class DemoController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/publish")
    public Long DoPublish(@RequestParam Long id) {
        log.info("I will publish {}.", id);
        rabbitTemplate.send("justEvent-in-0","justEvent", new Message(Longs.toByteArray(id)));
        rabbitTemplate.send("justDo","justDo", new Message(Longs.toByteArray(id)));
        log.info("I finished publish {}.", id);
        return id;
    }
}
