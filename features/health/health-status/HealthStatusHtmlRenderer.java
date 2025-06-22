package com.yourcompany.yourapp.features.health.health_status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class HealthStatusHtmlRenderer {

    private final TemplateEngine templateEngine;

    public String render(HealthStatus status) {
        Context context = new Context();
        context.setVariable("status", status);
        return templateEngine.process("HealthStatusPage", context);
    }
}
