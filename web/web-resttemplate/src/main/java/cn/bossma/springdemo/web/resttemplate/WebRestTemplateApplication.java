package cn.bossma.springdemo.web.resttemplate;

import cn.bossma.springdemo.web.resttemplate.model.Product;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@Slf4j
public class WebRestTemplateApplication implements ApplicationRunner {
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(WebRestTemplateApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var entity = restTemplate.getForEntity("http://localhost:8080/product/get/1", Product.class);
        log.info("getForEntity:{}", entity);
        entity.getHeaders().forEach((k, v) -> {
            log.info("{}:{}", k, v);
        });
        log.info("getForEntity->Object:{}", entity.getBody());

        var object = restTemplate.getForObject("http://localhost:8080/product/get/2", Product.class);
        log.info("getForObject:{}", object);

        var uri = UriComponentsBuilder
                .fromUriString("http://localhost:8080/product/get/{id}")
                .build(1);
        var uriObject = restTemplate.getForObject(uri, Product.class);
        log.info("uri-getForObject:{}", uriObject);

        var addUri = UriComponentsBuilder
                .fromUriString("http://localhost:8080/product/add")
                .build().toUri();
        Product product = Product.builder()
                .name("Toy")
                .price(BigDecimal.valueOf(199.99))
                .build();
        String addResponse = restTemplate.execute(addUri, HttpMethod.POST, request -> {
            request.getHeaders().add("Content-Type", "application/json");
            try (OutputStreamWriter osw = new OutputStreamWriter(request.getBody());
                 BufferedWriter bw = new BufferedWriter(osw)) {
                bw.write(JSON.toJSONString(product));
            }
        }, response -> {
            try (InputStreamReader isr = new InputStreamReader(response.getBody());
                 BufferedReader br = new BufferedReader(isr);
            ) {
                StringBuffer sb = new StringBuffer();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }
        });
        log.info("execute:{}", addResponse);

        ParameterizedTypeReference<List<Product>> listRef = new ParameterizedTypeReference<>() {
        };

        var list = restTemplate.exchange("http://localhost:8080/product/get", HttpMethod.GET
                , null, listRef);
        list.getBody().forEach(p -> {
            log.info("exchange:{}", p);
        });

        var stringList = restTemplate.getForEntity("http://localhost:8080/product/get", String.class);
        log.info("getForEntity:{}", stringList);
    }
}
