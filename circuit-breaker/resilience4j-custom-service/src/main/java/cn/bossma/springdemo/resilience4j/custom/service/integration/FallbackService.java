package cn.bossma.springdemo.resilience4j.custom.service.integration;

import cn.bossma.springdemo.resilience4j.custom.service.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FallbackService implements ProductService {

    private Exception cause;

    public FallbackService(Exception cause) {
        this.cause = cause;
    }

    @Override
    public ProductDto getByName(String name) {
        fallbackMethod("getByName");
        return null;
    }

    @Override
    public List<ProductDto> getAll() {
        fallbackMethod("getAll");
        return new ArrayList<>();
    }

    @Override
    public ProductDto getById(Long id) {
        fallbackMethod("getById");
        return null;
    }

    public void WriteLog(String logMessage) {
        var logger = Logger.getLogger("FallbackService");
        logger.info(logMessage + " " + this.cause.getMessage());
    }

    public ProductDto fallbackMethod(String method) {
        WriteLog("Into Feign Fallback " + method);
        return null;
    }
}
