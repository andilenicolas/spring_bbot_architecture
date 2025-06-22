```
src/main/java/com/yourcompany/yourapp
├── application/                             <-- Shared business services used across features
│   └── reporting/
│       └── ReportingService.java

├── core/                                    <-- Shared technical utilities and foundational components
│   ├── config/                              <-- Strongly typed configuration classes with `@ConfigurationProperties`
│   │   ├── PayloadConfig.java
│   │   ├── CamundaConfig.java
│   │   └── AppConfig.java                   <-- Meta-annotation to enforce standards
│   ├── json/                                <-- JSON utilities
│   │   ├── JacksonJsonUtils.java
│   │   └── MarshallerJsonUtils.java
│   ├── response/                            <-- API response builders and types
│   │   ├── Response.java
│   │   └── ResponseUtils.java
│   ├── datetime/                            <-- Date parsing & formatting helpers
│   │   ├── DateUtils.java
│   │   └── DateFormat.java
│   ├── exception/                           <-- Cross-cutting error handling
│   │   └── AppExceptionHandler.java
│   └── mediator/                            <-- Internal library to dispatch feature handlers (inspired by MediatR)
│       ├── Request.java
│       ├── Handler.java
│       └── Sender.java

├── infrastructure/
│   ├── client/                              <-- External service interaction
│   │   ├── camunda/
│   │   │   ├── CamundaClient.java
│   │   │   └── CamundaClientImpl.java
│   │   └── payloadrepository/
│   │       ├── PayloadRepositoryClient.java
│   │       └── PayloadRepositoryClientImpl.java
│   └── persistence/
│       ├── entity/
│       │   └── BaseEntity.java              <-- Superclass for all entities
│       └── repository/
│           ├── GenericRepository.java
│           ├── GenericRepositoryImpl.java
│           └── GenericRepositoryRegistrar.java

├── features/                                <-- Vertical slices: one folder per business capability
│   └── health/
│       ├── HealthController.java
│       └── health-status/
│           ├── HealthHandler.java          <-- Mediator-based request handling logic
│           ├── HealthStatusRequest.java    <-- Request model used with Sender
│           ├── HealthStatus.java
│           └── HealthStatusHtmlRenderer.java

└── App.java                                  <-- Application entry point

tests/
└── architecture/                             <-- ArchUnit tests for enforcing structure and conventions
    ├── FeatureControllerNamingRuleTest.java  <-- Enforces one controller per feature, proper naming
    ├── LayerDependencyRulesTest.java          <-- ✅ Updated: Enforces strict layering between packages
    ├── NoCyclicDependencyRuleTest.java        <-- ✅ Updated: Prevents cyclic dependencies across packages
    └── InterfaceInjectionRuleTest.java     <-- Enforce DI uses interfaces only
```
