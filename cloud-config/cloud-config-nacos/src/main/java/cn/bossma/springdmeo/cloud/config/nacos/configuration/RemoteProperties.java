package cn.bossma.springdmeo.cloud.config.nacos.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix ="labels")
@RefreshScope
@Data
@Component
public class RemoteProperties {
    private String name;
    private String city;
}
