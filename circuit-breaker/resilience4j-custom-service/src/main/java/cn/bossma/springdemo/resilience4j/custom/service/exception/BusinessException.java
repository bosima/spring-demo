package cn.bossma.springdemo.resilience4j.custom.service.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
