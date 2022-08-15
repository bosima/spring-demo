package cn.bossma.springdemo.custom.product.service.registry;

import com.ecwid.consul.v1.agent.model.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.context.ApplicationContext;

import java.net.URI;
import java.util.Map;

public class CustomRegistration implements Registration {

    NewService service;

    @Autowired
    ApplicationContext context;

    public CustomRegistration(NewService service) {
        this.service = service;
    }

    public NewService getNewService() {
        return this.service;
    }

    @Override
    public String getInstanceId() {
        return IdUtils.getDefaultInstanceId(context.getEnvironment(), false);
    }

    @Override
    public String getServiceId() {
        return this.service.getId();
    }

    @Override
    public String getHost() {
        return this.service.getPort().toString();
    }

    @Override
    public int getPort() {
        return this.service.getPort();
    }

    @Override
    public boolean isSecure() {
        return getUri().getScheme().equalsIgnoreCase("https");
    }

    @Override
    public URI getUri() {
        return DefaultServiceInstance.getUri(this);
    }

    @Override
    public String getScheme() {
        return getUri().getScheme();
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.service.getMeta();
    }
}
