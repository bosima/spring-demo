package cn.bossma.springdemo.mvc.exception.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @RequestMapping(method = RequestMethod.GET, path = "/get/{id}")
    public ProductDto getById(@PathVariable Long id) {
        if (id == 0) {
            throw new BusinessException("The id equals 0.", 1000);
        }
        if (id < 0) {
            throw new FaultException("The id less than 0.", 1001);
        }
        return ProductDto.builder()
                .id(1L)
                .name("Book")
                .description("I am a book!")
                .build();
    }
}
