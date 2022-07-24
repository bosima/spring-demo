package cn.bossma.springdemo.mvc.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public ErrorResponse businessExceptionHandler(BusinessException exception) {
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(FaultException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse faultExceptionHandler(FaultException exception) {
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }
}
