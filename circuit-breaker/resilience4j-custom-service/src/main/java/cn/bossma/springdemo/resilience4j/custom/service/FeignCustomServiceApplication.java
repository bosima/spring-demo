package cn.bossma.springdemo.resilience4j.custom.service;

import cn.bossma.springdemo.resilience4j.custom.service.integration.FallbackService;
import cn.bossma.springdemo.resilience4j.custom.service.integration.ProductService;
import com.fasterxml.jackson.databind.Module;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
@EnableAspectJAutoProxy
public class FeignCustomServiceApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(FeignCustomServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendA");
        RateLimiter rateLimiter = RateLimiter.ofDefaults("backendA");
        Bulkhead bulkhead = Bulkhead.ofDefaults("backendA");
        FeignDecorators decorators = FeignDecorators.builder()
                .withFallbackFactory(FallbackService::new)
                .withBulkhead(bulkhead)
                .withRateLimiter(rateLimiter)
                .withCircuitBreaker(circuitBreaker)
                .build();
        ProductService productService = Resilience4jFeign.builder(decorators)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ProductService.class, "http://localhost:64014/product");

        ThreadPoolExecutor executor=new ThreadPoolExecutor(3,6,1000, TimeUnit.MILLISECONDS
                ,new SynchronousQueue<Runnable>()
        ,Executors.defaultThreadFactory()
        ,new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            executor.execute(()->{
                try {
                    var product = productService.getById(1L);
                    log.info("getById:{}", product);
                } catch (Exception ex) {
                    log.error("Exception:" + ex.getMessage());
                }
            });

            executor.execute(()->{
                try {
                    var product = productService.getByName("Book");
                    log.info("getByName:{}", product);
                } catch (Exception ex) {
                    log.error("Exception:" + ex.getMessage());
                }
            });

            executor.execute(()->{
                try {
                    var products = productService.getAll();
                    log.info("getAll:{}", products);
                } catch (Exception ex) {
                    log.error("Exception:" + ex.getMessage());
                }
            });
        }
    }
}
