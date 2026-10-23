---
name: module-event
description: Add a domain event between modules using ApplicationEventPublisher and @ApplicationModuleListener (Spring Modulith). Use when one module must react to something that happened in another.
---

Event: $ARGUMENTS

## Publish (owning module)
1. Create an immutable `record` in `<module>/api/event/`, named in the past tense: `<Thing><Happened>Event` (e.g. `StudentAtRiskDetectedEvent`).
2. Carry ids and the minimal facts the listeners need (ids, old/new values, dates). Never carry entities. Use JSON-serialisable types only, because events are stored in `event_publication`.
3. Publish inside the transactional service method: `applicationEventPublisher.publishEvent(new XxxEvent(...))`. The event is delivered only if the transaction commits.

## Listen (other module)
1. In the listening module's `service/` package, add a Spring bean with a method annotated `@ApplicationModuleListener` (async, runs after commit in its own transaction, retried from the registry if it fails).
2. Listeners must be **idempotent**: an event can be delivered again after a crash.
3. Never call back into the publisher's internals. Use its `api/` if more data is needed.

## Test
- Use `@ApplicationModuleTest` + `Scenario` in the publishing module:
  `scenario.stimulate(() -> service.doThing(...)).andWaitForEventOfType(XxxEvent.class).toArriveAndVerify(e -> ...)`.
- Run `./gradlew test --tests '*ModularityTests'` to confirm no illegal dependency was introduced.
- Update the event flow list in `docs/ARCHITECTURE.md`.
