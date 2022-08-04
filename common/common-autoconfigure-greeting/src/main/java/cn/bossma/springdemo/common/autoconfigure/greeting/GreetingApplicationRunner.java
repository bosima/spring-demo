package cn.bossma.springdemo.common.autoconfigure.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
public class GreetingApplicationRunner implements ApplicationRunner {
    String name;

    public GreetingApplicationRunner() {
        this("Bosima");
    }

    public GreetingApplicationRunner(String name) {
        this.name = name;
        log.info("I am {}", name);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Hello {}", this.name);
    }
}
