# 🧭 Architecture Guide

Welcome to the architecture reference for this Spring Boot application. This document serves as a **practical onboarding and navigation guide** for engineers building and maintaining the codebase.

---

## 🔧 Principles & Architectural Styles

This codebase is structured with a thoughtful blend of:

- **Modular Architecture**: Logical boundaries by purpose or role
- **Domain-Driven Design (DDD)**: Richly expressed features and aggregates
- **Layered Architecture**: Clear separation of concerns (Core, Application, Infrastructure, Features)
- **Vertical Slice Architecture**: Features modeled as cohesive, testable modules

---

## 🗂️ Top-Level Folders

| Folder                | Responsibility                                                     |
| --------------------- | ------------------------------------------------------------------ |
| `application/`        | Reusable business logic/services across features (e.g., reports)   |
| `core/`               | Shared utilities, helpers, immutables, configs, internal libraries |
| `features/`           | Vertical slices of the app — each feature lives independently here |
| `infrastructure/`     | Technical details: persistence, external APIs, clients             |
| `tests/architecture/` | Rules enforced with ArchUnit to guard the intended structure       |

---

## 🧱 Features (Vertical Slice)

**Location:** `features/<feature-name>`

Each feature is encapsulated with its own handler, request, response model, and controller.

Example:

```
├── features/                                <-- Vertical slices: one folder per business capability
│   └── health/
│       ├── HealthController.java
│       └── health-status/                  <-- Use case per feature
│           ├── HealthHandler.java          <-- Mediator-based request handling logic
│           ├── HealthStatusRequest.java    <-- Request model used with Sender
│           ├── HealthStatus.java
│           └── HealthStatusHtmlRenderer.java
```

### ✅ Principles

- Only 1 controller per feature.
- Uses the `Sender` mediator to delegate to the appropriate handler.
- Adds no dependencies on other features directly.

---

## 🧠 Core (Cross-cutting & Reusable Logic)

| Subfolder   | Purpose                                                         |
| ----------- | --------------------------------------------------------------- |
| `config/`   | Typed, immutable `@ConfigurationProperties`-driven configs      |
| `mediator/` | Internal CQRS-like `Sender`, `Handler`, and `Request` interface |
| `json/`     | Jackson and JAXB serialization utilities                        |
| `response/` | Common DTOs (`Response`) and helper (`ResponseUtils`)           |
| `datetime/` | `DateUtils`, `DateFormat` enum for standard formatting          |

---

## 🔌 Infrastructure

| Subfolder                 | Purpose                                                              |
| ------------------------- | -------------------------------------------------------------------- |
| `clients/`                | External service integrations, grouped by tool/service               |
| `persistence/entity/`     | Domain models mapped to DB (Postgres via JPA) inherit `BaseEntity`   |
| `persistence/repository/` | Internal impls only if needed (minimized by generic repo), registrar |

---

## 🧪 Architecture Tests

**Location:** `tests/architecture/`

| File                         | Rule                                                |
| ---------------------------- | --------------------------------------------------- |
| `LayerDependencyRulesTest`   | Layer enforcement (no feature ↔ infra leakage, etc) |
| `NoCyclicDependencyRuleTest` | No cyclic deps between packages                     |
| `InterfaceInjectionRuleTest` | Enforces DI only via interfaces (@Autowired checks) |

---

## 📡 Internal Mediator

**Core Interfaces**

- `Request<T>` – Marker for types passed to `Sender`
- `Handler<TRequest, TResponse>` – Handles logic per request
- `Sender` – Injected into controllers to dispatch requests to handlers

**Why?**

- Keeps controllers clean (max 1 dependency)
- Decouples transport logic from processing logic
- Easily mockable in tests

---

## 🧾 Configuration Properties

- Stored in `core/config/*Config.java` files
- Must be prefixed via `@ConfigurationProperties("external.payload")` style
- Bound via Spring Boot, immutable by default (final + no public setters)
- Use custom `@AppConfig` annotation to enforce structure + javadoc reminder

---

## 🧬 Naming & Structure Conventions

- Feature folders = lowercase, hyphen-separated: `payment-history`
- Feature controller = `PaymentHistoryController`
- Only 1 controller per feature
- Shared logic = keep small and testable before adding to `application/` or `core/`

---

## 🪛 How to Extend

| Action                  | Where / How                                              |
| ----------------------- | -------------------------------------------------------- |
| Add new feature         | `features/<your-feature>` + controller + request/handler |
| Add shared service      | `application/`                                           |
| Add new config group    | `core/config/YourConfig.java` + `@AppConfig`             |
| Add new external client | `infrastructure/clients/<your-client>`                   |
| Add new DB entity       | `infrastructure/persistence/entity/`                     |
| Add arch rule           | `tests/architecture/`                                    |

---

Happy building! 🧱✨
