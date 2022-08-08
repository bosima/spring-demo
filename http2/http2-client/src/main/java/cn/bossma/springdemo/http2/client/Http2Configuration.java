package cn.bossma.springdemo.http2.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.time.Duration;

@Configuration
@Slf4j
public class Http2Configuration {
    @Value("${security.key-store}")
    Resource keyStore;

    @Value("${security.key-pass}")
    String keyPass;

    private ClientHttpRequestFactory httpRequestFactory() {
        OkHttpClient okHttpClient = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(this.keyStore.getInputStream(), keyPass.toCharArray());

            TrustManagerFactory trustManagerFactory
                    = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagerFactory.getTrustManagers()[0])
                    .hostnameVerifier(((hostname, session) -> true))
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
