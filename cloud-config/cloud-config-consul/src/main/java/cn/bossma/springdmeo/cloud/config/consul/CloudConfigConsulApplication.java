package cn.bossma.springdmeo.cloud.config.consul;

import cn.bossma.springdmeo.cloud.config.consul.configuration.RemoteProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class CloudConfigConsulApplication implements ApplicationRunner {
    @Autowired
    RemoteProperties conf;

    public static void main(String[] args) {
        SpringApplication.run(CloudConfigConsulApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("name:{}", conf.getName());
        log.info("city:{}", conf.getCity());
    }
}
