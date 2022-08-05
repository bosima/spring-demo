package cn.bossma.springdemo.common.autoconfigure.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.ClassUtils;

@Slf4j
public class GreetingBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        boolean beanIsDefined = configurableListableBeanFactory.containsBeanDefinition("greetingApplicationRunner");
        if (beanIsDefined) {
            log.info("We already have a greetingApplicationRunner bean registered.");
            return;
        }

        boolean classIsPresent = ClassUtils.isPresent("cn.bossma.springdemo.common.autoconfigure.greeting.GreetingApplicationRunner",
                GreetingBeanFactoryPostProcessor.class.getClassLoader());
        if (!classIsPresent) {
            log.info("GreetingApplicationRunner is NOT present in CLASSPATH.");
            return;
        }

        register(configurableListableBeanFactory);
    }

    private void register(ConfigurableListableBeanFactory configurableListableBeanFactory) {
        if (configurableListableBeanFactory instanceof BeanDefinitionRegistry) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(GreetingApplicationRunner.class);

            ((BeanDefinitionRegistry) configurableListableBeanFactory).registerBeanDefinition(
                    "greetingApplicationRunner", beanDefinition
            );
            log.info("greetingApplicationRunner is registered by BeanDefinitionRegistry.");
        } else {
            configurableListableBeanFactory
                    .registerSingleton("greetingApplicationRunner", new GreetingApplicationRunner());
            log.info("greetingApplicationRunner is registered by registerSingleton.");
        }
    }
}
