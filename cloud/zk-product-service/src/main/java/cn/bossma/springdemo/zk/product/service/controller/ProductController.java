package cn.bossma.springdemo.zk.product.service.controller;

import cn.bossma.springdemo.zk.product.service.controller.dto.NewProductRequest;
import cn.bossma.springdemo.zk.product.service.controller.dto.ProductDto;
import cn.bossma.springdemo.zk.product.service.error.BusinessError;
import cn.bossma.springdemo.zk.product.service.model.Product;
import cn.bossma.springdemo.zk.product.service.error.ArgumentException;
import cn.bossma.springdemo.zk.product.service.service.ProductService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public ProductDto getByName(@NotNull String name) {
        var product = productService.getByName(name);
        return modelMapper.map(product, ProductDto.class);
    }

    @GetMapping(path = "/", params = "!name")
    public List<ProductDto> getAll() {
        var productList = productService.getAll();
        List<ProductDto> dtoList = new ArrayList<>();
        for (var product : productList) {
            dtoList.add(modelMapper.map(product, ProductDto.class));
        }
        return dtoList;
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        var product = productService.getById(id);
        return modelMapper.map(product, ProductDto.class);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductDto add(@RequestBody ProductDto productDto) {
        var product = modelMapper.map(productDto, Product.class);
        product = productService.insert(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ProductDto add(@Valid NewProductRequest request, BindingResult result) {
        if (result.hasErrors()) {
            log.error("The request arguments format is invalid!");
            throw new ArgumentException("The request arguments format is invalid!", 1000);
        }

        var product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .build();
        product = productService.insert(product);
        return modelMapper.map(product, ProductDto.class);
    }

    // In some cases, errors handled at the controller level are not recorded by the metrics infrastructure.
    @ExceptionHandler(ArgumentException.class)
    BusinessError handleArgumentException(HttpServletRequest request, ArgumentException ex) {
        request.setAttribute(ErrorAttributes.ERROR_ATTRIBUTE, ex);
        return new BusinessError(ex.getMessage(), ex.getCode());
    }
}
