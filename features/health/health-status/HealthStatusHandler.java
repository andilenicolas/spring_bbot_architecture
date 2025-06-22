package com.yourcompany.yourapp.features.health.health_status;

import com.yourcompany.yourapp.core.mediator.Handler;
import org.springframework.stereotype.Component;

@Component
public class HealthStatusHandler implements Handler<HealthStatusRequest, HealthStatus> {

    @Override
    public HealthStatus handle(HealthStatusRequest request) {
        // Simple static health info, can be extended
        return new HealthStatus("UP", "All systems operational.");
    }
}
