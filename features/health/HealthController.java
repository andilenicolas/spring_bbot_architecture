package com.yourcompany.yourapp.features.health;

import com.yourcompany.yourapp.core.mediator.Sender;
import com.yourcompany.yourapp.core.response.Response;
import com.yourcompany.yourapp.core.response.ResponseUtils;
import com.yourcompany.yourapp.features.health.health_status.HealthStatus;
import com.yourcompany.yourapp.features.health.health_status.HealthStatusHtmlRenderer;
import com.yourcompany.yourapp.features.health.health_status.HealthStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final Sender sender;
    private final HealthStatusHtmlRenderer htmlRenderer;

    @GetMapping("/health")
    public ResponseEntity<Response<HealthStatus>> getHealth() {
        HealthStatus status = sender.send(new HealthStatusRequest());
        return ResponseUtils.success("Application healthy", status);
    }

    @GetMapping(value = "/healthz", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getHealthPage() {
        HealthStatus status = sender.send(new HealthStatusRequest());
        return ResponseEntity.ok(htmlRenderer.render(status));
    }
}
