package cn.bossma.springdemo.http2.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class Http2Configuration {
    private ConnectionPool pool() {
        return new ConnectionPool(10, 60, TimeUnit.SECONDS);
    }

    private ClientHttpRequestFactory httpRequestFactory() {
        OkHttpClient okHttpClient = null;
        try {
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectionPool(pool())
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(2, TimeUnit.SECONDS)
                    .writeTimeout(1, TimeUnit.SECONDS)
                    //.hostnameVerifier((hostname, session) -> true)
                    .build();

        } catch (Exception ex) {
            log.error("Exception occurred while creating SSLContext", ex);
        }

        return new OkHttp3ClientHttpRequestFactory(okHttpClient);
    }

    @Bean
    public RestTemplate resetTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(1))
                .requestFactory(this::httpRequestFactory)
                .build();
    }
}
