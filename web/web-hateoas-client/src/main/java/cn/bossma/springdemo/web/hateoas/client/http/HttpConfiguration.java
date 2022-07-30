package cn.bossma.springdemo.web.hateoas.client.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class HttpConfiguration {
    @Bean
    RestTemplate hypermediaRestTemplate(HypermediaRestTemplateConfigurer configurer, RestTemplateBuilder builder) {
        var template = builder
                .setConnectTimeout(Duration.ofMillis(100))
                .setReadTimeout(Duration.ofMillis(500))
                .requestFactory(this::requestFactory)
                .build();
        return configurer.registerHypermediaTypes(template);
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory requestFactory() {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(30);
        connectionManager.setDefaultMaxPerRoute(5);
        connectionManager.closeIdleConnections(60, TimeUnit.SECONDS);

        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .disableAutomaticRetries()
                .evictExpiredConnections()
                .evictIdleConnections(60, TimeUnit.SECONDS)
                .setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
                .build();

        return new HttpComponentsClientHttpRequestFactory(client);
    }
}
