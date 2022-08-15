package cn.bossma.springdemo.custom.product.service.registry;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.health.HealthChecksForServiceRequest;
import com.ecwid.consul.v1.health.model.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

public class CustomServiceRegistry implements ServiceRegistry<CustomRegistration> {

    @Autowired
    ApplicationContext context;

    @Autowired
    ConsulClient consulClient;

    @Override
    public void register(CustomRegistration registration) {
        if (registration == null) return;
        consulClient.agentServiceRegister(registration.getNewService());
    }

    @Override
    public void deregister(CustomRegistration registration) {
        if (registration == null) return;
        consulClient.agentServiceDeregister(registration.getServiceId());
    }

    @Override
    public void close() {
    }

    @Override
    public void setStatus(CustomRegistration registration, String status) {
        if (status.equalsIgnoreCase(Status.OUT_OF_SERVICE.getCode())) {
            consulClient.agentServiceSetMaintenance(registration.getServiceId(), true);
        } else {
            if (!status.equalsIgnoreCase(Status.UP.getCode())) {
                throw new IllegalArgumentException("Unknown status: " + status);
            }

            consulClient.agentServiceSetMaintenance(registration.getServiceId(), false);
        }
    }

    @Override
    public Object getStatus(CustomRegistration registration) {
        var checksResponse = consulClient.getHealthChecksForService(registration.getServiceId(), HealthChecksForServiceRequest.newBuilder().build());
        if (checksResponse.getValue() != null && checksResponse.getValue().size() > 0) {
            for (var check : checksResponse.getValue()) {
                if (check.getStatus() != Check.CheckStatus.PASSING) {
                    return Status.OUT_OF_SERVICE.getCode();
                }
            }

            return Status.UP.getCode();
        }

        return Status.OUT_OF_SERVICE.getCode();
    }
}
