package com.yourcompany.yourapp.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ConfigurationProperties(prefix = "")
public @interface AppConfig {
    /**
     * Defines the property prefix (e.g., "external.payload").
     * Must be set for Spring to bind config properly.
     */
    @AliasFor(annotation = ConfigurationProperties.class, attribute = "prefix")
    String value();
}
