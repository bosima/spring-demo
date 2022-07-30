package cn.bossma.springdemo.web.hateoas.client;

import cn.bossma.springdemo.web.hateoas.client.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.mediatype.hal.HalLinkDiscoverer;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootApplication
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
@Slf4j
public class WebHateoasClientApplication {

    private static final URI BASE_URI = URI.create("http://localhost:8080");

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(WebHateoasClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public ApplicationRunner run() {
        return args -> {
            Link productLink = GetLink(BASE_URI, "products");
            var rsp = restTemplate.exchange(productLink.getTemplate().expand(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<PagedModel<Product>>() {
                    });
            log.info("GET /products: {}", rsp.getBody().getContent());

            Product toy = Product.builder()
                    .name("Toy")
                    .price(Money.of(CurrencyUnit.of("CNY"), 16.99))
                    .build();

            RequestEntity<Product> req =
                    RequestEntity.post(productLink.getTemplate().expand()).body(toy);
            try {
                ResponseEntity<Product> resp =
                        restTemplate.exchange(req, Product.class);
                log.info("POST /products: {}", resp.getBody());
            } catch (HttpClientErrorException ex) {
                log.error("POST /products exception:\n{}", ex.getResponseBodyAsString());
            }
        };
    }

    private Link GetLink(URI uri, String rel) {
        var jsonStr = restTemplate.getForObject(uri, String.class);
        LinkDiscoverer discoverer = new HalLinkDiscoverer();
        Link link = discoverer.findLinkWithRel(rel, jsonStr).orElse(null);
        log.info("Link: {}", link);
        return link;
    }
}
