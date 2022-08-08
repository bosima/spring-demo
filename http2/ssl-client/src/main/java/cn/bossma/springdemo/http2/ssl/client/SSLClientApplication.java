package cn.bossma.springdemo.http2.ssl.client;

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
public class SSLClientApplication implements ApplicationRunner {
    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder(SSLClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var result = restTemplate.exchange("https://localhost:8443/product/1",
                HttpMethod.GET, null, String.class);

        log.info(result.getBody());
    }
}
