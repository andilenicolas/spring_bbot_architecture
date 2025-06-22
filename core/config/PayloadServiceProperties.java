package com.yourcompany.yourapp.core.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration for the external payload repository service.
 */
@AppConfig("external.payload")
@Getter
@Setter
public class PayloadServiceProperties {
    private String baseUrl;
    private int timeoutSeconds;
}
