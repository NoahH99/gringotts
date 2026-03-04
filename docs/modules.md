# Module Overview

Gringotts is structured as a multi-module Gradle project. Each module has a single, well-defined
responsibility. Dependencies only flow inward — toward `kernel` — never outward.

---

## Dependency Graph

```
runtime
├── kernel
├── ledger
│   └── kernel
├── gateway
│   └── kernel
├── infra-persistence
│   ├── kernel
│   └── ledger
└── infra-observability
```

---

## Modules

### `kernel`
**Role:** Core domain layer.

The innermost module. Contains shared domain types, value objects, interfaces, and any logic that
belongs to the problem domain itself rather than to any infrastructure concern. Has no external
library dependencies — only the JDK and the test framework.

Nothing in `kernel` knows about Spring, JDA, JPA, or any other framework.

---

### `ledger`
**Role:** Financial business logic.

Implements the accounting and transaction rules of the system — account balances, transfers, audit
trails, and any other financial operations. Depends only on `kernel`. Has no knowledge of how data
is stored or how commands arrive.

---

### `gateway`
**Role:** Discord bot interface.

Owns the JDA lifecycle and all inbound Discord interactions (slash commands, message events, button
clicks, etc.). Translates raw Discord events into calls against domain logic. Depends on `kernel`
for domain types; does not depend on `ledger` or any infra module directly.

Spring beans in this module are conditional on `DISCORD_TOKEN` being set, so the application starts
cleanly in environments without a bot token.

---

### `infra-persistence`
**Role:** Database access layer.

Contains all JPA entities, Spring Data repositories, and Flyway migrations. Responsible for
mapping domain objects from `kernel` and `ledger` to relational storage. Depends on `kernel` and
`ledger`; knows nothing about Discord or HTTP.

Flyway migrations live under `src/main/resources/db/migration/` and version the schema
incrementally.

---

### `infra-observability`
**Role:** Metrics and health reporting.

Exposes operational visibility via Spring Boot Actuator and Micrometer. Publishes a Prometheus
scrape endpoint at `/actuator/prometheus`. No domain or persistence dependencies — observability
is purely an infrastructure concern.

Actuator endpoints exposed: `health`, `info`, `metrics`, `prometheus`.

---

### `runtime`
**Role:** Application entry point and wiring.

The only module that carries the Spring Boot plugin and produces an executable fat-jar. Its sole
job is to declare all other modules as dependencies and boot the application. Contains no business
logic of its own.

Component scanning is set to `com.noahhendrickson` so Spring discovers beans across all modules.

---

## Adding a New Module

1. Create the directory and a `build.gradle.kts` with the appropriate dependencies.
2. Add the module name to `settings.gradle.kts` under `include(...)`.
3. Add `implementation(project(":your-module"))` in `runtime/build.gradle.kts`.
4. Keep the dependency rule: modules may only depend on `kernel`, `ledger`, or other domain
   modules — never on `infra-*` modules.
