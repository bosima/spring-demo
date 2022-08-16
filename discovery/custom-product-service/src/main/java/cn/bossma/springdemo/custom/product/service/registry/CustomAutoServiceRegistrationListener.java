package cn.bossma.springdemo.custom.product.service.registry;

import org.springframework.boot.web.context.ConfigurableWebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

public class CustomAutoServiceRegistrationListener implements SmartApplicationListener {
    CustomAutoServiceRegistration registration;

    public CustomAutoServiceRegistrationListener(CustomAutoServiceRegistration registration) {
        this.registration = registration;
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return WebServerInitializedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof WebServerInitializedEvent) {
            WebServerInitializedEvent initEvent = (WebServerInitializedEvent)event;
            ApplicationContext context = initEvent.getApplicationContext();
            if (context instanceof ConfigurableWebServerApplicationContext && "management".equals(((ConfigurableWebServerApplicationContext)context).getServerNamespace())) {
                return;
            }

            this.registration.setPort(initEvent.getWebServer().getPort());
            this.registration.start();
        }
    }
}
