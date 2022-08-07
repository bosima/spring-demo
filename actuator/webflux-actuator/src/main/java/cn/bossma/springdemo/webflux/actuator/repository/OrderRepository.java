package cn.bossma.springdemo.webflux.actuator.repository;

import cn.bossma.springdemo.webflux.actuator.model.Product;
import cn.bossma.springdemo.webflux.actuator.model.ProductOrder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
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
        return template.select(ProductOrder.class)
                .matching(Query.query(Criteria.where("id").is(id)).limit(1))
                .first()
                .flatMap(po ->
                        databaseClient.sql("select p.* from t_product p,t_order_product o where" +
                                        " p.id=o.product_id" +
                                        " and o.order_id=:order_id")
                                .bind("order_id", id)
                                .map((row,obj)-> Product.builder()
                                        .id(row.get("id",Long.class))
                                        .name(row.get("name",String.class))
                                        .price(Money.ofMinor(CurrencyUnit.of("CNY"), row.get("price",Long.class)))
                                        .createTime(row.get("create_time",LocalDateTime.class))
                                        .updateTime(row.get("update_time",LocalDateTime.class))
                                        .build())
                                .all()
                                .collectList()
                                .flatMap(l -> {
                                    po.setProducts(new ArrayList<>());
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
