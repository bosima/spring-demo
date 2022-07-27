package cn.bossma.springdemo.web.resttemplate.component;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
public class RestTemplateComponent {

    private final long DEFAULT_SECONDS = 30;

    @Bean
    public HttpComponentsClientHttpRequestFactory httpRequestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(40);
        connectionManager.setDefaultMaxPerRoute(8);
        connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .disableAutomaticRetries()
                .setKeepAliveStrategy((rsp, ctx) -> {
                    return Arrays.asList(rsp.getHeaders(HTTP.CONN_KEEP_ALIVE))
                            .stream()
                            .filter(s -> s.getName().equalsIgnoreCase("timeout")
                                    && StringUtils.isNumeric(s.getValue()))
                            .findFirst()
                            .map(h -> NumberUtils.toLong(h.getValue(), DEFAULT_SECONDS))
                            .orElse(DEFAULT_SECONDS) * 1000;
                }).build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .setConnectTimeout(Duration.ofMillis(300))
                .setReadTimeout(Duration.ofMillis(1000))
                .requestFactory(this::httpRequestFactory)
                .build();
    }
}
