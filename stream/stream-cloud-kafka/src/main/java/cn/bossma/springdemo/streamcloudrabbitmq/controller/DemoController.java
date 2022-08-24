package cn.bossma.springdemo.streamcloudrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
@Slf4j
public class DemoController {
    @Autowired
    KafkaTemplate<String, Long> kafkaTemplate;

    @GetMapping("/publish")
    public Long DoPublish(@RequestParam Long id) {
        log.info("I will publish {}.", id);
        kafkaTemplate.send("justEvent-in-0", "justEvent", id);
        log.info("I finished publish {}.", id);
        return id;
    }
}
