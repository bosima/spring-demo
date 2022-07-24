package cn.bossma.springdemo.mvc.exception.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class FaultException extends RuntimeException {
    private Integer code;

    public FaultException(String message, Integer code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public FaultException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
