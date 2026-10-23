package com.kshrd.hrdintelligence.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Enables async event listeners ({@code @ApplicationModuleListener} runs async) and scheduled jobs (data sync,
 * weekly summaries). Scheduling can be switched off with {@code app.scheduling.enabled=false}, e.g. in tests.
 */
@Configuration
@EnableAsync
class AsyncSchedulingConfig {

    @Configuration
    @EnableScheduling
    @ConditionalOnProperty(name = "app.scheduling.enabled", havingValue = "true", matchIfMissing = true)
    static class SchedulingConfig {}
}
