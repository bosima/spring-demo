package cn.bossma.springdemo.custom.product.service.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessError {
    private String msg;
    private Integer code;
}
