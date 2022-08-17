package cn.bossma.springdemo.hystrix.custom.service.integration;

import cn.bossma.springdemo.hystrix.custom.service.dto.ProductDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Logger;

@FeignClient(name = "product-service", contextId = "product", path = "/product"
        ,fallback = FallbackService.class)
public interface ProductService {
    @GetMapping(path = "/", params = "name")
    ProductDto getByName(@RequestParam String name);

    @GetMapping(path = "/", params = "!name")
    List<ProductDto> getAll();

    @GetMapping("/{id}")
    ProductDto getById(@PathVariable Long id);
}
