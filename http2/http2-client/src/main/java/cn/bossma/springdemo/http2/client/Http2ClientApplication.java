package cn.bossma.springdemo.http2.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class Http2ClientApplication implements ApplicationRunner {
    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Http2ClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var result = restTemplate.exchange("https://localhost:9443/product/1",
                HttpMethod.GET, null, String.class);

        log.info(result.getBody());
    }
}
