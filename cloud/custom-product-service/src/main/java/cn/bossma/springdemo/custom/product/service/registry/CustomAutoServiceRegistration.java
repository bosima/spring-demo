package cn.bossma.springdemo.custom.product.service.registry;

import com.ecwid.consul.v1.agent.model.NewService;
import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;

public class CustomAutoServiceRegistration extends AbstractAutoServiceRegistration<CustomRegistration> {
    CustomRegistration registration;

    protected CustomAutoServiceRegistration(CustomServiceRegistry serviceRegistry, AutoServiceRegistrationProperties properties) {
        super(serviceRegistry, properties);
    }

    public void start() {
        super.start();
    }

    public void stop() {
        super.stop();
    }

    public void setPort(int port) {
        super.getPort().compareAndSet(0, port);
    }

    @Override
    protected Object getConfiguration() {
        return this.registration;
    }

    @Override
    protected boolean isEnabled() {
        return true;
    }

    @Override
    protected CustomRegistration getRegistration() {
        var context = super.getContext();
        InetUtils netUtils = new InetUtils(new InetUtilsProperties());

        String serviceName = context.getEnvironment().getProperty("spring.application.name", "UNKNOWN");
        NewService service = new NewService();
        service.setId(IdUtils.getDefaultInstanceId(context.getEnvironment(), true));
        service.setAddress(netUtils.findFirstNonLoopbackHostInfo().getIpAddress());
        service.setName(serviceName);
        service.setPort(super.getPort().get());

        NewService.Check serviceCheck = new NewService.Check();
        serviceCheck.setHttp("http://" + service.getAddress() + ":" + service.getPort() + "/actuator/health");
        serviceCheck.setInterval("10s");
        service.setCheck(serviceCheck);

        CustomRegistration registration = new CustomRegistration(service);
        this.registration = registration;

        return this.registration;
    }

    @Override
    protected CustomRegistration getManagementRegistration() {
        return null;
    }
}
