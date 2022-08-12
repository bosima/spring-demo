package cn.bossma.springdemo.eureka.server.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessError {
    private String msg;
    private Integer code;
}
