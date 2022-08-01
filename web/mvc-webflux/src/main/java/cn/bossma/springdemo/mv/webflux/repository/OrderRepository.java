package cn.bossma.springdemo.mv.webflux.repository;

import cn.bossma.springdemo.mv.webflux.model.OrderState;
import cn.bossma.springdemo.mv.webflux.model.Product;
import cn.bossma.springdemo.mv.webflux.model.ProductOrder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class OrderRepository {
    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    R2dbcEntityTemplate template;

    public Mono<ProductOrder> getById(Long id) {
        return databaseClient.sql("select * from t_order where id=" + id)
                .map((row, obj) -> ProductOrder.builder()
                        .id(row.get("id", Long.class))
                        .state(OrderState.values()[row.get("state", Integer.class)])
                        .customer(row.get("customer", String.class))
                        .createTime(row.get("create_time", LocalDateTime.class))
                        .updateTime(row.get("update_time", LocalDateTime.class))
                        .products(new ArrayList<>())
                        .build()
                )
                .first()
                .flatMap(po ->
                        databaseClient.sql("select p.* from t_product p,t_order_product o where" +
                                        " p.id=o.product_id" +
                                        " and o.order_id=:order_id")
                                .bind("order_id", id)
                                .fetch()
                                .all()
                                .flatMap(map ->
                                        Mono.just(Product.builder()
                                                .id((Long) map.getOrDefault("id", 0L))
                                                .name((String) map.getOrDefault("name", ""))
                                                .price(Money.ofMinor(CurrencyUnit.of("CNY"), (Long) map.getOrDefault("price", 0L)))
                                                .createTime((LocalDateTime) map.get("create_time"))
                                                .updateTime((LocalDateTime) map.get("update_time"))
                                                .build())
                                )
                                .collectList()
                                .flatMap(l -> {
                                    po.getProducts().addAll(l);
                                    return Mono.just(po);
                                })
                );
    }

    @Transactional
    public Mono<Long> save(ProductOrder order) {
        return template.insert(ProductOrder.class)
                .into("t_order")
                .using(order)
                .map(o -> o.getId())
                .flatMap(id ->
                        Flux.fromIterable(order.getProducts())
                                .flatMap(p ->
                                        databaseClient.sql("insert into t_order_product values(:order_id,:product_id)")
                                                .bind("order_id", id)
                                                .bind("product_id", p.getId())
                                                .then())
                                .then(Mono.just(id)));
    }
}
