---
name: verify
description: Run the full quality gate (formatting, compile, tests, module boundaries, coverage) and report the real results. Use before saying a task is done.
---

1. Run `./gradlew spotlessApply` to fix formatting.
2. Run `./gradlew check`. It runs the Spotless check, all tests (including `ModularityTests`, which verifies module boundaries) and the JaCoCo report. Tests need Docker running for Testcontainers.
3. If something fails, read the failure in `build/test-results/test/*.xml` or `build/reports/tests/test/index.html`, fix the cause (not the test, unless the test is wrong), and rerun.
4. Report honestly:
   - the command(s) run and PASS/FAIL
   - the number of tests run/failed
   - any failure with its key error lines
   - the coverage report location: `build/reports/jacoco/test/html/index.html`

Never claim success without having run the command in this session.
