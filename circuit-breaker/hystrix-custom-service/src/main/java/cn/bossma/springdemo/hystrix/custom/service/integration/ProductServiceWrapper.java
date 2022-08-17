package cn.bossma.springdemo.hystrix.custom.service.integration;

import cn.bossma.springdemo.hystrix.custom.service.dto.ProductDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.logging.Logger;

@Service
public class ProductServiceWrapper {
    @Autowired
    ProductService productService;

    @HystrixCommand(fallbackMethod = "fallbackGetById")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    public void WriteLog(String logMessage) {
        var logger = Logger.getLogger("ProductServiceWrapper");
        logger.info(logMessage);
    }

    public ProductDto fallbackGetById(Long id) {
        WriteLog("Into HystrixCommand Fallback ");
        return null;
    }
}
