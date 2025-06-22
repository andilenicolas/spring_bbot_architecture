# 🧠 Architecture Overview: Modular Clean Architecture with Vertical Slices

## 📌 Summary

This project follows a **Modular Clean Architecture** pattern enhanced with **Vertical Slice Design** and Domain-Driven Design principles.

It brings together the strengths of multiple architectural approaches to promote clarity, scalability, testability, and business alignment — all within a structured, modular setup.

---

## 🧱 Architecture Layers

| Layer                  | Description                                                                                                                     |
| ---------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| **Features**           | Vertical slices per domain use case (e.g., `health`). Contains controllers, handlers, and request/response DTOs.                |
| **Core**               | Shared, stateless, side-effect-free components like the Mediator, typed configs, JSON/date utilities, response builders.        |
| **Application**        | Pure business logic. Contains application services, contracts, enums, and logic orchestration (e.g., report generators).        |
| **Infrastructure**     | Interfacing layer with the outside world — HTTP clients, JPA persistence, repository registrars, external service integrations. |
| **Tests/Architecture** | ArchUnit rules and architecture enforcement: naming, dependency rules, layering constraints.                                    |

---

## 🧭 Architectural Style Breakdown

### 🧼 Clean Architecture (Robert C. Martin)

- **Dependency Rule**: Code dependencies point inwards, toward use cases.
- **Infrastructure (Spring, JPA, HTTP)** is replaceable.
- **Business logic is framework-agnostic.**

### 🧩 Modular Architecture

- Code is divided into independent units (e.g., `health`, `reporting`).
- Features are **plug-and-play** and encapsulate their full lifecycle.

### 🎯 Vertical Slice Architecture

- Each feature (e.g., `health-status`) is built as a **cohesive unit**: controller → handler → service.
- Reduces coupling between features and encourages **use-case thinking** over CRUD-based layering.

### 🧠 Domain-Driven Design (DDD - Lightweight)

- Use-case-first structure.
- Clear separation of responsibilities (commands, handlers, services).
- Use of contracts and enums for **ubiquitous language**.

---

## 🧪 Enforced with ArchUnit

To enforce and safeguard architectural boundaries, the project uses:

- `NoCyclicDependencyRuleTest` — prevents circular package dependencies.
- `LayerDependencyRulesTest` — validates layer integrity.
- `InjectionOnInterfacesTest` — enforces interface-based dependency injection.

---

## 🛠 Benefits

- ✅ Separation of concerns
- ✅ High modularity and testability
- ✅ Clear onboarding and maintenance structure
- ✅ Extensible for future features or services

---

## 📦 Suggested File: `ARCHITECTURE.md`

You may include this file in the root of the project to onboard engineers and explain the architectural thinking behind the codebase.
