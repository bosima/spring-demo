package cn.bossma.springdemo.custom.product.service.registry;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter({AutoServiceRegistrationConfiguration.class, CustomServiceRegistryConfiguration.class})
public class CustomAutoServiceRegistrationConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CustomAutoServiceRegistration consulAutoServiceRegistration(CustomServiceRegistry registry, AutoServiceRegistrationProperties autoServiceRegistrationProperties) {
        return new CustomAutoServiceRegistration(registry, autoServiceRegistrationProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomAutoServiceRegistrationListener consulAutoServiceRegistrationListener(CustomAutoServiceRegistration registration) {
        return new CustomAutoServiceRegistrationListener(registration);
    }
}
