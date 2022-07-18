package cn.bossma.springdemo.common.reactor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@Slf4j
public class CommonReactorApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(CommonReactorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Flux.range(1, 5)
                .doOnRequest(d -> System.out.println("doOnRequest " + d))
                .publishOn(Schedulers.parallel())
                .doOnComplete(() -> System.out.println("doOnComplete 1"))
                .map(d -> {
                    System.out.println("map " + d);
                    d = 10 / (d - 4);
                    return d;
                })
                .doOnComplete(() -> System.out.println("doOnComplete 2"))
//                .onErrorResume(e->{
//                    return Mono.just(-1);
//                })
                .subscribe(d -> System.out.println("subscribe " + d + "," + Thread.currentThread().getId())
                        , e -> System.out.println("e.getMessage() = " + e.getMessage())
                        , () -> System.out.println("subscribe completed"));
    }
}