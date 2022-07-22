package cn.bossma.springdemo.common.aop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_product")
public class Product implements Serializable {
    @Id
    @GeneratedValue( )
    private Long id;

    private String name;

    private String description;

    private Date addTime;

    private Date updateTime;
}
