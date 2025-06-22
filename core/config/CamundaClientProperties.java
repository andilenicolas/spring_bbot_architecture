package com.yourcompany.yourapp.core.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration for the external Camunda client integration.
 */
@AppConfig("external.camunda")
@Getter
@Setter
public class CamundaClientProperties {
    private String baseUrl;
    private String authToken;
}
