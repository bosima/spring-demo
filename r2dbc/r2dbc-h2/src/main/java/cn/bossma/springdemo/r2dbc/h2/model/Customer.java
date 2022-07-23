package cn.bossma.springdemo.r2dbc.h2.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private Long id;

    @NonNull
    private String firstName;

    private String lastName;
}
