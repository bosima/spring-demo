package cn.bossma.springdemo.r2dbc.h2.repository;

import cn.bossma.springdemo.r2dbc.h2.model.Customer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
    @Query("select * from customer where first_name=:firstname")
    Flux<Customer> findAllByFistName(String firstName);
}
