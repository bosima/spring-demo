package cn.bossma.springdemo.mvc.restful.controller;

import cn.bossma.springdemo.mvc.restful.dto.ProductDto;
import cn.bossma.springdemo.mvc.restful.model.Product;
import cn.bossma.springdemo.mvc.restful.service.ProductService;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/get")
    public ProductDto getByName(@NotNull String name) {
        var product = productService.getByName(name);
        return modelMapper.map(product, ProductDto.class);
    }

    @GetMapping("/get/{id}")
    public ProductDto getById(@PathVariable Long id) {
        var product = productService.getById(id);
        return modelMapper.map(product, ProductDto.class);
    }

    @PostMapping(path = "/add", consumes = "application/json")
    public Long add(ProductDto productDto) {
        var product = modelMapper.map(productDto, Product.class);
        product = productService.insert(product);
        return product.getId();
    }
}
