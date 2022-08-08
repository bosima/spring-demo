package cn.bossma.springdemo.http2.ssl.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class HttpConfiguration {
    @Value("${security.key-store}")
    Resource keyStore;

    @Value("${security.key-pass}")
    String keyPass;

    private HttpComponentsClientHttpRequestFactory httpRequestFactory() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(keyStore.getURL(), keyPass.toCharArray())
                    //.loadTrustMaterial(null, (cert, s) -> true)
                    .setKeyStoreType("PKCS12")
                    .build();
        } catch (Exception ex) {
            log.error("Exception occurred while creating SSLContext", ex);
        }

        CloseableHttpClient client = HttpClients.custom()
                .disableAutomaticRetries()
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(5)
                .setConnectionTimeToLive(60, TimeUnit.SECONDS)
                .evictIdleConnections(60, TimeUnit.SECONDS)
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        return new HttpComponentsClientHttpRequestFactory(client);
    }

    @Bean
    public RestTemplate resetTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(1))
                .requestFactory(this::httpRequestFactory)
                .build();
    }
}
