package cn.bossma.springdemo.feign.custom.service.integration;

import cn.bossma.springdemo.feign.custom.service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product.service", contextId = "product", path = "product")
public interface ProductService {
    @GetMapping(path = "/", params = "name")
    ProductDto getByName(@RequestParam String name);

    @GetMapping(path = "/", params = "!name")
    List<ProductDto> getAll();

    @GetMapping("/{id}")
    ProductDto getById(@PathVariable Long id);
}
