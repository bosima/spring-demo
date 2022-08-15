package cn.bossma.springdemo.consul.product.service.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArgumentException extends RuntimeException {
    private Integer code;

    public ArgumentException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
