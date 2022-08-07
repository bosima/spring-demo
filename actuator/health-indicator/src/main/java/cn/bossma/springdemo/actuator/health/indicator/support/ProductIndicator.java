package cn.bossma.springdemo.actuator.health.indicator.support;

import cn.bossma.springdemo.actuator.health.indicator.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("product")
public class ProductIndicator implements HealthIndicator {

    @Autowired
    ProductService productService;

    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }

    @Override
    public Health health() {
       var count =  productService.findAll()
                .collectList()
                .map(l->l.size())
                .block();

       Health health;
       if(count>0){
           health = Health.up()
                   .withDetail("count",count)
                   .withDetail("message","We can sale some things.")
                   .build();
       }else{
           health = Health.down()
                   .withDetail("count",count)
                   .withDetail("message","We have sold out.")
                   .build();
       }

       return health;
    }
}
