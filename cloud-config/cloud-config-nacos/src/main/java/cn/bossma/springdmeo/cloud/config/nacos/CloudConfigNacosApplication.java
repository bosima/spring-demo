package cn.bossma.springdmeo.cloud.config.nacos;

import cn.bossma.springdmeo.cloud.config.nacos.configuration.RemoteProperties;
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
public class CloudConfigNacosApplication implements ApplicationRunner {
    @Autowired
    RemoteProperties conf;

    public static void main(String[] args) {
        // enable bootstrap.properties
        //System.setProperty("spring.cloud.bootstrap.enable", "true");

        // use legacy style process config
        //System.setProperty("spring.config.use-legacy-processing", "true");

        var context = SpringApplication.run(CloudConfigNacosApplication.class, args);
        log.info("labels.name:{}", context.getEnvironment().getProperty("labels.name"));
        log.info("labels.city:{}", context.getEnvironment().getProperty("labels.city"));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("name:{}", conf.getName());
        log.info("city:{}", conf.getCity());
    }
}
