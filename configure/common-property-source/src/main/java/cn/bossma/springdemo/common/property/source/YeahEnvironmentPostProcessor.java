package cn.bossma.springdemo.common.property.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
public class YeahEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        var propertySource = environment.getPropertySources();
        var resource = new ClassPathResource("yeah.properties");

        try {
            var ps = loader.load("YeahPropertiesFile", resource).get(0);
            propertySource.addLast(ps);
        } catch (Exception ex) {
            log.error("Add propertySource error.", ex);
        }
    }
}
