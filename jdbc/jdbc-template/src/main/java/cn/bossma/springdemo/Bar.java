package cn.bossma.springdemo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bar {
    public Long id;
    public String foo;
}
