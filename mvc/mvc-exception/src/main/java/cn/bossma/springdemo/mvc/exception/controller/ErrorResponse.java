package cn.bossma.springdemo.mvc.exception.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    public Integer code;
    public String message;
}
