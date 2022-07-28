package cn.bossma.springdemo.web.hateoas.model;

import lombok.*;
import org.joda.money.Money;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "t_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Money price;
    private Timestamp createTime;
    private Timestamp updateTime;
}