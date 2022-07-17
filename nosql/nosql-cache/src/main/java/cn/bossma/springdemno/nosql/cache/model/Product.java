package cn.bossma.springdemno.nosql.cache.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "t_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @Id // javax.persistence.*
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String name;

    @Column(name = "desc", length = 255)
    private String description;

    @CreationTimestamp
    private Timestamp addTime;

    @UpdateTimestamp
    private Timestamp updateTime;
}