package cn.bossma.cn.springdemo.mvc.interceptor;

import cn.bossma.cn.springdemo.mvc.interceptor.interceptor.PerformanceInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@SpringBootApplication
public class MvcInterceptorApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(MvcInterceptorApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PerformanceInterceptor()).
                addPathPatterns("/product/**");
    }

    @Bean
    Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
        return builder -> {
            builder.indentOutput(true);
            //builder.simpleDateFormat("yyyy-MM-dd'T'HH:mm:sss.SSSXXX"); //ISO8601
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        };
    }
}
