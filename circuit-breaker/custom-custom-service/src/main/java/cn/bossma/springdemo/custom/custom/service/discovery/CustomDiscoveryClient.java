package cn.bossma.springdemo.custom.custom.service.discovery;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.List;

public class CustomDiscoveryClient implements DiscoveryClient {
    @Autowired
    ConsulClient consulClient;

    @Override
    public String description() {
        return "Custom Consul Discovery Client";
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        List<ServiceInstance> list = new ArrayList();

        var serviceListResponse = consulClient.getHealthServices(serviceId, HealthServicesRequest.newBuilder().build());
        var serviceList = serviceListResponse.getValue();
        if (serviceList.size() == 0) {
            return list;
        }

        serviceList.forEach(s -> {
            DefaultServiceInstance si = new DefaultServiceInstance();
            si.setHost(s.getService().getAddress());
            si.setPort(s.getService().getPort());
            si.setServiceId(s.getService().getId());
            si.setInstanceId(s.getNode().getId());
            list.add(si);
        });

        return list;
    }

    @Override
    public List<String> getServices() {
        return null;
    }
}
