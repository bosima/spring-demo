package cn.bossma.springdemo.resilience4j.custom.service.integration;

import cn.bossma.springdemo.resilience4j.custom.service.dto.ProductDto;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {
    @RequestLine("GET /?name={name}")
    ProductDto getByName(@Param("name") String name);

    @RequestLine("GET /")
    List<ProductDto> getAll();

    @RequestLine("GET /{id}")
    ProductDto getById(@Param("id") Long id);
}
