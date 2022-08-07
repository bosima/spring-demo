package cn.bossma.springdemo.webflux.actuator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_order")
public class ProductOrder {
    @Id
    private Long id;
    private String customer;
    private OrderState state;
    @Transient
    private List<Product> products;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
